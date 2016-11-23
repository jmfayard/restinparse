package com.github.jmfayard

import com.github.jmfayard.SelfieModel.Event
import com.github.jmfayard.SelfieModel._User
import com.github.jmfayard.model.ParseFile
import com.github.jmfayard.model.ParseResultSchemas.ParseSchema
import com.github.jmfayard.model.ParseUser
import com.natpryce.konfig.*
import io.kotlintest.matchers.Matcher
import org.joda.time.LocalDate
import org.joda.time.LocalDateTime
import java.io.File
import java.util.*

class ApiTests : RxSpec() {
    object parse : PropertyGroup() {
        val applicationId by stringType
        val masterKey by stringType
        val restKey by stringType
        val restApiUrl by stringType
        val sessionToken by stringType
        val username by stringType
        val password by stringType
    }

    val selfiedev = ConfigurationProperties.systemProperties() overriding
            EnvironmentVariables() overriding
            ConfigurationProperties.fromResource("selfiedev.properties")
    val instance = selfiedev

    init {

        RestInParse.configure()
                .applicationId(instance[parse.applicationId])
                .masterKey(instance[parse.masterKey])
                .restKey(instance[parse.restKey])
                .logLevel(RestInParse.LogLevel.DEBUG)
                .restApiUrl(instance[parse.restApiUrl])
                .apply()



        RestInParse.startMasterSession()

        val sessionToken = instance[parse.sessionToken]
        val credentials = instance[parse.username] to instance[parse.password]

        val userFields = arrayOf("username", "objectId", "updatedAt", "createdAt")
        val basicFields = arrayOf("objectId", "updatedAt", "createdAt")

//        feature("Register then delete user") {
//            val random = Random().nextInt()
//            val map = mapOf(
//                    _User.username to "wowowowow$random",
//                    _User.password to "ppppppppp",
//                    _User.email to "wowowowow$random@byom.com"
//            )
//
//            val register = RestInParse.registerUser(map)
//            val delete = register.flatMap { user: ParseObject<_User> ->
//                _User.table().delete(user.id())
//            }
//            rxScenario("register then delete", delete) {
//                this.debug("works!")
//            }
//        }

        feature("Objects") {
            val fetchEvents = Event.table().query()
                    .limit(3)
                    .include(Event.affects)
                    .exists(Event.affects)
                    .build().findAll()
            val fetchEventById = fetchEvents
                    .take(1)
                    .flatMap { result: ParseObject<Event> ->
                        val id = result.getSring(SelfieModel.Event.objectId)
                        return@flatMap Event.table().findById(id)
                    }

            rxScenario("GET objects by query", fetchEvents) {
                debug("Event")
                map().get("affects").debug("affects")
                map().keys should containInAnyOrder(*basicFields)
                getPointer(Event.actor).debug("actor") should notBeNull
//TODO                getParseUser(Event.affects).debug("affects") should notBeNull
//TODO                getParseObject<_User>(Event.affects).debug("affects") should notBeNull
            }
            rxScenario("GET object by id", fetchEventById) {
                map().keys should containInAnyOrder(*basicFields)

            }
        }


        feature("Users") {
            val query = _User.table().query()
                    .equalToString(_User.username, "restinparse")
                    .build()
            rxScenario("get values from object", query.findAll()) {
                this.debug("parsedebug")
                getSring(_User.gender).debug("gender") shouldBe "MALE"
                getInt(_User.rate).debug("rate") shouldBe 5
                getDouble(_User.rate).debug("rate") shouldBe 5.0
                getBoolean(_User.privacyMode) shouldBe true
                val file = getFile(_User.image)!!.debug("image")
                file.name.isNotBlank() shouldBe true
                file.url.isNotBlank() shouldBe true
                getSring(_User.username) shouldBe "restinparse"
                val birthday = LocalDateTime.fromDateFields(getDate(_User.birthday))
                birthday.year shouldBe 1981

                getFile(_User.birthday) shouldBe null
                getDate(_User.image) shouldBe null
                getPointer(_User.image) shouldBe null
                getParseObject<ParseUser>(_User.image) shouldBe null
                getParseUser(_User.image) shouldBe null
            }
        }

        feature("Updating objects") {
            val cities = listOf("Berlin", "Paris", "Cartago")
            val city = cities[Random().nextInt(cities.size)]
            val updates = hashMapOf(
                    _User.locality to city,
                    _User.countryCode to "+49"
            ) as Map<_User, Any>
            val jmf = _User.table().pointer("pBb9nBXGjP")
            val observable = _User.table().update(jmf, updates)
                .flatMap {
                    _User.table().findById(jmf)
                }
            rxScenario("Updating user", observable) {
                map().debug("User after update")
                getSring(_User.locality) shouldBe city
            }
        }

        feature("Parse Schema") {
            rxScenario("SCHEMA", RestInParse.schemas()) {
                val tables = results.map { schema: ParseSchema -> schema.className }
                tables.debug("Found Parse Tables")
                results.isNotEmpty() shouldBe true
                val schema: ParseSchema = results.first()
                schema.fields.keys should containInAnyOrder(*basicFields)
            }
        }



        feature("Users") {
            val fetchUsers = _User.table().query().limit(3).build().findAll()
            val fetchEventById = fetchUsers
                    .take(1)
                    .flatMap { result: ParseObject<_User> ->
                        return@flatMap Event.table().findById(result.id())
                    }

            rxScenario("GET objects by query", fetchUsers) {
                map().keys should containInAnyOrder(*basicFields)
            }
            rxScenario("GET object by id", fetchEventById) {
                map().keys should containInAnyOrder(*basicFields)
                map().debug("Event")
            }
        }


        feature("Loging in") {
            rxScenario("... with a session token", RestInParse.checkSessionToken(sessionToken)) {
                map().keys should containInAnyOrder(*userFields)
                map() should haveKey("sessionToken")
            }

            rxScenario("... with username & password", RestInParse.checkLogin(credentials.first, credentials.second)) {
                map().keys should containInAnyOrder(*userFields)
                map() should haveKey("sessionToken")
            }
        }

        feature("Queries") {
            val age18 = LocalDate.now().minusYears(18).toDate()
            val age15 = LocalDate.now().minusYears(15).toDate()

            val query = _User.table().query()
                    .newerThan(_User.birthday, age18)
                    .olderThan(_User.birthday, age15)
                    .limit(1000)
                    .build()


            rxScenario("Querying users between 15 and 18", query.findAll().take(3)) {
                map().keys should containInAnyOrder(*userFields)
            }
        }

        feature("Uploading files") {
            val file = File("src/test/resources/file.txt")
            assert(file.canRead()) { file.absolutePath }
            var parseFile: ParseFile? = null
            val destination = File("build/destination.txt")
            rxScenario("Upload", RestInParse.uploadFile(file, "text/plain")) {
                parseFile = this
                parseFile.debug("File Result")
                name.isNotBlank() shouldBe true
            }

        }


    }


}

object notBeNull : Matcher<Any?> {
    override fun test(value: Any?) {
        checkNotNull(value)
    }

}
