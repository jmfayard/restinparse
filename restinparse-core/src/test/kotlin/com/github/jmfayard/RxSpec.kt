package com.github.jmfayard

import io.kotlintest.specs.FeatureSpec
import retrofit2.Response
import retrofit2.http.HTTP
import rx.Observable
import rx.Single
import kotlin.test.assertTrue


abstract class RxSpec : FeatureSpec() {

    protected fun <T> retrofitFeature(name: String, call: Single<Response<T>>, operation: T.() -> Unit) =
            feature(name) {
                var response: Response<T> = call.toBlocking().value()
                var data: T? = null
                if (response.isSuccessful) {
                    val body = response.body()
                    body.operation()
                } else {
                    scenario("Call $name failed with a server error") {
                        "HTTP ${response.message()}" shouldBe "HTTP 200"
                    }
                }
            }

    protected fun <T> retrofitScenario(name: String, call: Single<Response<T>>, operation: T.() -> Unit) {
        scenario(name) {
            val response = call.toBlocking().value()
            if (response.isSuccessful) {
                val body = response.body()
                body.operation()
            } else {
                "HTTP ${response.message()}" shouldBe "HTTP 200"
            }

        }
    }
    protected fun <T> retrofitScenario(name: String, call: Observable<Response<T>>, operation: T.() -> Unit)
            = retrofitScenario(name, call.take(1).toSingle(), operation)

}



//
//    protected fun <T> retrofitScenarioShouldFail(name: String, call: Single<Response<ApiResponse<T>>>) =
//        scenario(name) {
//            val response = call.toBlocking().value()
//            assert(response.isSuccessful) {
//                "[$name] Expected an api error, but got HTTP ${response.message()}"
//            }
//            val data = response.body()
//            assert(data.isError) {
//                "[$name] Expected an api error, but got HTTP OK"
//            }
//            println(
//                "[$name] Got api error as expected (code=${data.apiCode()})"
//            )
//        }

//
//    fun <T> assertSuccess(response: Response<ApiResponse<T>>) : T {
//        if (response.isSuccessful) {
//            val data = response.body()
//            if (data.isSuccess) {
//                return  data.data
//            } else {
//                data.apiCode() shouldBe 0
//                return data.data
//            }
//        } else {
//            println("API error")
//            throw  AssertionError("API Error")
//        }
//    }


//
//    fun logSubscriber(name: String): Action1<Any> {
//        println("[$name] => started")
//        return object : Action1<Any> {
//            override fun call(t: Any?) {
//                println("[$name] => got $t")
//            }
//
//        }
//    }
//
//    fun assertCanDownloadFile(url: String?) {
//        if (url==null) {
//            return
//        }
//        val url2 = if (url.contains("localhost")) {
//            url.replace("https", "http")
//        } else {
//            url
//        }
//        val request = Request.Builder()
//            .url(url2)
//            .build()
//
//        val response = ok.newCall(request).execute()
//        assert(response.isSuccessful) {
//            "ERROR, cannot download file [$url]"
//        }
//    }
