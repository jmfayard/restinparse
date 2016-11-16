package com.github.jmfayard

import com.github.jmfayard.SelfieModel._User
import com.github.jmfayard.model.ParseResultSchemas.ParseSchema
import com.github.jmfayard.model.ParseObject
import com.natpryce.konfig.*
import org.joda.time.LocalDate

class ApiTests : RxSpec() {
    object instance : PropertyGroup() {
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

    init {

        RestInParse.Initializer()
                .applicationId(selfiedev[instance.applicationId])
                .masterKey(selfiedev[instance.masterKey])
                .restKey(selfiedev[instance.restKey])
                .logLevel(RestInParse.LogLevel.NONE)
//                .restApiParseDotCom()
                .restApiUrl(selfiedev[instance.restApiUrl])
                .initialize()


        val parse: ParseClient = RestInParse.masterClient()
        val sessionToken = selfiedev[instance.sessionToken]
        val credentials = selfiedev[instance.username] to selfiedev[instance.password]

        val userFields = arrayOf("username", "objectId", "updatedAt", "createdAt")
        val basicFields = arrayOf("objectId", "updatedAt", "createdAt")


        feature("Parse Schema") {
            retrofitScenario("SCHEMA", parse.schemas()) {
                val list: List<ParseSchema> = this.results
//            list.forEach {table : ParseSchema ->
//                table.fields
//                table.fields.debug("Table[${table.className}]")
//            }
                list.isNotEmpty() shouldBe true
            }
        }
        feature("Objects") {
            val query = SelfieModel.Event.query().limit(3).build()


            val fetchEvents = query.findAll()


            val fetchEventById = fetchEvents
                    .take(1)
                    .flatMap { result: ParseObject<SelfieModel.Event> ->
                        val id = result.getSring(SelfieModel.Event.objectId)
                        return@flatMap query.findById(id)
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


            retrofitScenario("GET object by id", parse.fetchUser("DW69kOMzXp")) {
                map().keys should containInAnyOrder(*basicFields)
            }
        }


        feature("Loging in") {
            retrofitScenario("... with a session token", parse.me(sessionToken)) {
                map().keys should containInAnyOrder(*userFields)
                map() should haveKey("sessionToken")
            }

            retrofitScenario("... with username & password", parse.login(credentials.first, credentials.second)) {
                map().keys should containInAnyOrder(*userFields)
                map() should haveKey("sessionToken")
            }
        }

        feature("Queries") {
            val age18 = LocalDate.now().minusYears(18).toDate()
            val age15 = LocalDate.now().minusYears(15).toDate()

            val query = SelfieModel._User.query()
                    .newerThan(_User.birthday, age18)
                    .olderThan(_User.birthday, age15)
                    .limit(1000)
                    .build()


            rxScenario("Querying users between 15 and 18", query.findAll().take(3)) {
                map().keys should containInAnyOrder(*userFields)
            }
        }


    }

}

