package com.github.jmfayard

import com.squareup.moshi.FromJson
import com.squareup.moshi.Moshi
import com.squareup.moshi.ToJson
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*

import com.natpryce.konfig.*
import java.io.File




/** https://en.wikipedia.org/wiki/List_of_HTTP_header_fields **/
enum class H(val v: kotlin.String) {
    CacheControl("Cache-Control"),
    ETag("ETag"),
    ContentLength("Content-Length"),
    Server("Content-Length"),
    ContentType("Content-Type"),
    Accept("Accept"),
    Authorization("Authorization"),
    Connection("Connection"),
    Cookie("Cookie"),
    UserAgent("User-Agent"),
    Pragma("Pragma"),
    Origin("Origin"),
    IfNoneMatch("If-None-Match"),
    IfModifiedSince("If-Modified-Since"),
    IfMatch("If-Match"),
    Host("Host"),
    XRequestedWith("X-Requested-With"),

}

fun <T> Response<T>.header(header: H): String = headers().get(header.v)

fun buildRetrofit(init: Retrofit.Builder.() -> Unit): Retrofit {
    val builder = Retrofit.Builder()
    builder.init()
    return builder.build()
}

fun buildOk(init: OkHttpClient.Builder.() -> Unit): OkHttpClient {
    val builder = OkHttpClient.Builder()
    builder.init()
    return builder.build()
}

fun buildRequest(init: Request.Builder.() -> Unit): Request {
    val builder = Request.Builder()
    builder.init()
    return builder.build()
}

fun Request.Builder.buildUrl(init: HttpUrl.Builder.() -> Unit): Unit {
    val builder = HttpUrl.Builder()
    builder.init()
    url(builder.build())
}

val moshi = Moshi.Builder()
        .add(DateAdapter())
        .build()


internal class DateAdapter {
    @ToJson fun toJson(date: Date): String {
        return date.time.toString()
    }

    @FromJson fun fromJson(date: String): Date {
        return Date()
    }
}


//infix fun HaveWrapper<out JsonNode>.schema(schema: JsonNode): Unit {
//    val validator: JsonSchema = JsonUtils.validator(schema)
//    val report = validator.validate(value)
//
//    if (!report.isSuccess)
//        throw AssertionError("Json does match the expected json schema\n$value\n${report.toString()}")
//}
//infix fun HaveWrapper<out JsonNode>.schema(@Language("File") schemaPath: String): Unit {
//    val validator: JsonSchema = JsonUtils.validator(schemaPath)
//    val report = validator.validate(value)
//
//    if (!report.isSuccess)
//        throw AssertionError("Json does match the expected json schema\n$value\n${report.toString()}")
//}
//
//fun <T>  CollectionMatchers.containAll(vararg t: T): Matcher<Collection<T>> = object : Matcher<Collection<T>> {
//    val collection = t.map { it }
//    override fun test(value: Collection<T>) {
//        val notfound = collection - value
//        if (notfound.isNotEmpty())
//            throw AssertionError("Those elements were not found in the list $notfound")
//    }
//
//}
//
//
//
//object JsonUtils {
//
//    val cfg = LoadingConfiguration.newBuilder().dereferencing(Dereferencing.INLINE).freeze()
//    val factory = JsonSchemaFactory.newBuilder().setLoadingConfiguration(cfg).freeze()
//    fun json(string: String): JsonNode = JsonLoader.fromString(string)
//    fun  validator(schema: JsonNode): JsonSchema = factory.getJsonSchema(schema)
//    fun  validator( @Language("File") schemaPath: String): JsonSchema {
//
//        val file = when {
//            schemaPath.startsWith("/") -> File(schemaPath)
//            System.getProperty("user.dir").endsWith("httpplayground") ->   File("ok-retrofit/src/test/resources/$schemaPath")
//            else -> File("src/test/resources/$schemaPath")
//        }
//        val schema = JsonLoader.fromFile(file)
//        return factory.getJsonSchema(schema)
//    }
//}
//
//fun ObjectNode.keys() : List<String> = this.fieldNames().asSequence().toList()
//
//fun ObjectNode.childObject(name: String): ObjectNode {
//    if (get(name) is ObjectNode) {
//        return get(name) as ObjectNode
//    } else {
//        throw AssertionError("No child object with name [$name] in \n   $this")
//    }
//}