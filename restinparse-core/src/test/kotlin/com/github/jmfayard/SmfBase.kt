package com.github.jmfayard

import com.github.jmfayard.BaseModel.*
import com.github.jmfayard.model.ParseFile
import com.github.jmfayard.model.ParseMap
import com.github.jmfayard.model.ParseUser
import com.natpryce.konfig.*
import io.kotlintest.specs.FeatureSpec
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import rx.Observable
import rx.Single
import java.util.*

class SmfBase : FeatureSpec() {
    init {
        initParse()
        tests()
//        newTests()
    }

    fun newTests() {

    }

    object parse : PropertyGroup() {
        val applicationId by stringType
        val masterKey by stringType
        val restKey by stringType
    }

    fun initParse() {
        val smfbase = ConfigurationProperties.systemProperties() overriding
                EnvironmentVariables() overriding
                ConfigurationProperties.fromResource("selfiebase.properties")
        val instance = smfbase

        RestInParse.configure()
                .applicationId(instance[parse.applicationId])
                .masterKey(instance[parse.masterKey])
                .restKey(instance[parse.restKey])
                .logLevel(RestInParse.LogLevel.INFO)
                .restApiUrlOfParseDotCom()
                .apply()
    }


    fun tests() {


        RestInParse.startMasterSession()
        feature("Comment") {
            val query = Comment.table().query().exists(Comment.parent).include(Comment.parent).build()
            rxScenario("Includes", query.find()) { o ->
                o.getParseObject<Post>(Comment.parent).debug("parent") should notBeNull
            }
        }
        feature("GameScore") {
            val query = GameScore.table().query().exists(GameScore.user).include(GameScore.user).limit(3).build()
            rxScenario("Includes", query.find()) { o ->
                o.getParseUser(GameScore.user).debug("user") should notBeNull
            }
        }

        feature("GameScore") {

            RestInParse.startMasterSession()
            val attributes: ParseMap = GameScore.table()
                    .createMap()
                    .set(GameScore.cheatMode, true)
                    .set(GameScore.playerName, "Sean Plott")
                    .set(GameScore.skills, listOf("pwnage", "flying"))
                    .set(GameScore.score, 1)
                    .build()

            val createAndFetch = GameScore.table().create(attributes).flatMap { score ->
                GameScore.table().findById(score.id())
            }

            rxScenario("Create GameScore and fetch it by its id", createAndFetch) { score ->
                score.getBoolean(GameScore.cheatMode) shouldBe true
                score.getInt(GameScore.score) shouldBe 1
                score.getSring(GameScore.playerName) shouldBe "Sean Plott"
                val list: List<String> = score.getList<String>(GameScore.skills)
                list.size shouldBe 2
            }

        }

        feature("BigObject") {

            /** Attributes we will set for BigObject **/
            fun bigobjectAttributes(parseFile: ParseFile) : ParseMap {
                return BigObject.table()
                        .createMap()
                        .set(BigObject.file, parseFile)
                        .set(BigObject.number, 42)
                        .set(BigObject.bool, true)
                        .set(BigObject.string, "Hello")
                        .set(BigObject.array, listOf("string", 42, true))
                        .set(BigObject.user, _User.table().pointer("3GzRwZuYcG"))
                        .set(BigObject.score, GameScore.table().pointer("nZv7pflPVi"))
                        .set(BigObject.`object`, mapOf("ok" to true))
                        .set(BigObject.date, Date())
                        .build()
            }

            RestInParse.startMasterSession()

            /** First upload body, then use the result and create a BigObject, then fetch it again from Parse by its id **/
            val body = RequestBody.create(MediaType.parse("text/plain"), "Hello world!")
            val observable = RestInParse.uploadFile("hello.txt", body)
                    .flatMap { parseFile ->
                        val attributes = bigobjectAttributes(parseFile)
                        BigObject.table().create(attributes)

                    }.flatMap { bigObject ->
                        BigObject.table().findById(bigObject.id())
                    }


            rxScenario("Upload a file, create a BigObject and fetch it", observable) { o ->
                o.getInt(BigObject.number) shouldBe 42
                o.getBoolean(BigObject.bool) shouldBe true
                o.getSring(BigObject.string) shouldBe "Hello"
                o.getPointer(BigObject.user)!!.className shouldBe _User.table().className()
                o.getPointer(BigObject.score)!!.className shouldBe GameScore.table().className()
                checkNotNull(o.getJsonObject(BigObject.`object`)) should haveKey("ok")


            }

        }


        feature("Anonymous cloud function") {

            RestInParse.startAnonymousSession()
            val call: Observable<String> = RestInParse.callCloudFunctionReturningString("hello", mapOf("name" to "Philip", "greeting" to "Hallo"))
            call.subscribe(::println, ::println)
            rxScenario("Hello!", call) { result ->
                result shouldBe "Hallo Philip!"
            }


        }

        feature("Logged-in cloud function") {
            val login = RestInParse.checkLogin("jmfsmf", "pppppp")
            val loginThenCall = login.flatMap { result: ParseObject<ParseUser> ->
                val sessionToken = result.getSring(ParseUser.sessionToken).debug("sessionToken")
                sessionToken.isNullOrBlank() shouldBe false
                RestInParse.startUserSession(sessionToken)

                RestInParse.callCloudFunctionReturningString("hellouser", kotlin.collections.mapOf("greeting" to "Bonjour"))
            }

            rxScenario("Login then call hellouser!", loginThenCall) { result ->
                result shouldBe "Bonjour jmfsmf!"
            }
        }

        feature("Master cloud function") {
            RestInParse.startMasterSession()
            val call = RestInParse.callCloudFunctionReturningList<Any>("hellos", emptyMap())
            rxScenario("Call hellos", call) { results ->
                val list = results.filterIsInstance(Map::class.java) as List<Map<String, Any>>
                list.forEach { elem -> elem should haveKey("result") }
            }
        }
    }




}


fun <T> FeatureSpec.rxScenario(name: String, source: Observable<T>, operation: (next: T) -> Unit) {
    val blocking = source.toBlocking().iterator
    var i = 0
    while (true) {
        try {
            if (blocking.hasNext() == false) {
                return
            }
        } catch (e: Throwable) {
            scenario("$name Crash at step #$i") {
                throw AssertionError(e.message)
            }
            return
        }
        val step = blocking.next()
        i++
        scenario("$name #$i") {
            operation(step!!)
        }
    }
}

