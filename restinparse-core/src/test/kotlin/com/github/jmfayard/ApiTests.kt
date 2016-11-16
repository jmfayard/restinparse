package com.github.jmfayard

import com.github.jmfayard.SelfieModel.Event
import com.github.jmfayard.SelfieModel._User
import com.github.jmfayard.model.ParseFile
import com.github.jmfayard.model.ParseObject
import com.github.jmfayard.model.ParseResultSchemas.ParseSchema
import com.natpryce.konfig.*
import org.joda.time.LocalDate
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
                .logLevel(RestInParse.LogLevel.NONE)
//                .restApiParseDotCom()
                .restApiUrl(instance[parse.restApiUrl])
                .startMasterSession()
                .apply()


        val sessionToken = instance[parse.sessionToken]
        val credentials = instance[parse.username] to instance[parse.password]

        val userFields = arrayOf("username", "objectId", "updatedAt", "createdAt")
        val basicFields = arrayOf("objectId", "updatedAt", "createdAt")

        feature("Updating objects") {
            val cities = listOf("Berlin", "Paris", "Cartago")
            val city = cities[Random().nextInt(cities.size)]
            val updates = hashMapOf(
                    _User.locality to city,
                    _User.countryCode to "+49"
            ) as Map<_User, Any>
            val jmf = _User.table().pointer("pBb9nBXGjP")
            rxScenario("Updating user", _User.table().update(jmf, updates)) {
                this.debug("User after update")
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

        feature("Objects") {
            val fetchEvents = Event.table().query().limit(3).build().findAll()
            val fetchEventById = fetchEvents
                    .take(1)
                    .flatMap { result: ParseObject<SelfieModel.Event> ->
                        val id = result.getSring(SelfieModel.Event.objectId)
                        return@flatMap Event.table().findById(id)
                    }

            rxScenario("GET objects by query", fetchEvents) {
                map().keys should containInAnyOrder(*basicFields)
            }
            rxScenario("GET object by id", fetchEventById) {
                map().keys should containInAnyOrder(*basicFields)
                map().debug("Event")
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
            rxScenario("Upload", RestInParse.uploadFile(file)) {
                parseFile = this
                parseFile.debug("File Result")
                name.isNotBlank() shouldBe true
            }

//            rxScenario("Download", RestInParse.downloadFile(parseFile, destination)) {
//
//            }

        }


    }

}

