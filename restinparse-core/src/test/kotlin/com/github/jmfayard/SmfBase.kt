package com.github.jmfayard

import com.github.jmfayard.model.ParseUser
import com.natpryce.konfig.*
import rx.Observable

class SmfBase : RxSpec() {
    object parse : PropertyGroup() {
        val applicationId by stringType
        val masterKey by stringType
        val restKey by stringType
    }

    val smfbase = ConfigurationProperties.systemProperties() overriding
            EnvironmentVariables() overriding
            ConfigurationProperties.fromResource("selfiebase.properties")
    val instance = smfbase

    init {

        RestInParse.configure()
                .applicationId(instance[parse.applicationId])
                .masterKey(instance[parse.masterKey])
                .restKey(instance[parse.restKey])
                .logLevel(RestInParse.LogLevel.NONE)
                .restApiUrlOfParseDotCom()
                .apply()


        feature("Anonymous session") {
            RestInParse.startAnonymousSession()
            val call: Observable<String> = RestInParse.callCloudFunctionReturningString("hello", mapOf("name" to "Philip", "greeting" to "Hallo"))
            call.subscribe(::println, ::println)
            rxScenario("Hello!", call) {
                this shouldBe "Hallo Philip!"
            }


        }

        feature("User session") {
            val login = RestInParse.checkLogin("jmfsmf", "pppppp")
            val loginThenCall = login.flatMap { result: ParseObject<ParseUser> ->
                val sessionToken = result.getSring(ParseUser.sessionToken).debug("sessionToken")
                sessionToken.isNullOrBlank() shouldBe false
                RestInParse.startUserSession(sessionToken)

                RestInParse.callCloudFunctionReturningString("hellouser", kotlin.collections.mapOf("greeting" to "Bonjour"))
            }

            rxScenario("Login then call hellouser!", loginThenCall) {
                this shouldBe "Bonjour jmfsmf!"
            }


        }

        feature("Master session") {
            RestInParse.startMasterSession()
            val call = RestInParse.callCloudFunctionReturningList<Any>("hellos", emptyMap())
            rxScenario("Call hellos", call) {
                val list = this.filterIsInstance(Map::class.java) as List<Map<String, Any>>
                list.forEach { elem -> elem should haveKey("result") }
            }
        }


    }
}