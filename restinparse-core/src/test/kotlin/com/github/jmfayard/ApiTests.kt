package com.github.jmfayard

import com.github.jmfayard.SelfieModel._User
import com.github.jmfayard.internal.ParseRestApi
import com.github.jmfayard.internal.ParseResultSchemas.ParseSchema
import com.github.jmfayard.model.QueryResults
import com.github.jmfayard.model.Something
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
        val userid by stringType
        val objectId by stringType
    }

    val selfiedev = ConfigurationProperties.systemProperties() overriding
            EnvironmentVariables() overriding
            ConfigurationProperties.fromResource("selfiedev.properties")

    init {

        RestInParse.Initializer()
                .applicationId(selfiedev[instance.applicationId])
                .masterKey(selfiedev[instance.masterKey])
                .restKey(selfiedev[instance.restKey])
//                .restApiParseDotCom()
                .restApiUrl(selfiedev[instance.restApiUrl])
                .initialize()


        val parse: ParseRestApi = RestInParse.masterClient()
        val sessionToken = selfiedev[instance.sessionToken]
        val credentials = selfiedev[instance.username] to selfiedev[instance.password]

        val userFields = arrayOf("username", "objectId", "updatedAt", "createdAt", "ACL")
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
            val fetchEvents = parse.query(query.className, query.params)
            val fetchEventById = fetchEvents
                    .flatMap { response ->
                        val o = response.body().results.first()
                        val id = o.map().get("objectId") as String
                        parse.fetch("Event", id)

                    }

            retrofitScenario("GET objects by query", fetchEvents) {
                results.size shouldBe 3
            }
            retrofitScenario("GET object by id", fetchEventById) {
                map().keys should containInAnyOrder(*basicFields)
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


            retrofitScenario("Querying users between 15 and 18", parse.query(query.className, query.params)) {
                val list: List<Something> = this.results
                list.isNotEmpty() shouldBe true
                list.take(3).forEachIndexed { i, user ->
                    user.debug("User[$i]")
                }
            }
        }


    }

}

