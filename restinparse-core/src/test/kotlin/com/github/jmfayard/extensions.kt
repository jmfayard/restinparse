package com.github.jmfayard

import io.kotlintest.specs.FeatureSpec
import okhttp3.ResponseBody
import okio.BufferedSink
import okio.BufferedSource
import okio.Okio
import rx.Single
import java.io.File

fun <T> T.debug(name: String): T {
    println("DEBUG: ${name} = ${toString()}")
    return this
}

fun BufferedSink.newLine() = writeUtf8("\n")


fun File.okSource(): BufferedSource = Okio.buffer(Okio.source(this))

fun File.okSink(): BufferedSink = Okio.buffer(Okio.sink(this))

fun File.okAppendingSink(): BufferedSink = Okio.buffer(Okio.appendingSink(this))


fun <T> FeatureSpec.rxScenario(name: String, source: Single<T>, operation: (next: T) -> Unit) =
        rxScenario(name, source.toObservable(), operation)

fun <T : Any> checkSuccessfull(response: retrofit2.Response<T>): T =
        if (response.isSuccessful) {
            response.body()
        } else {
            throw AssertionError(""""Rertrofit failed with HTTP ${response.code()} ${response.message()}
${truncate(response.errorBody()?.string())}""")
        }

fun checkSuccessfull(response: okhttp3.Response): ResponseBody =
        if (response.isSuccessful) {
            response.body()
        } else {
            throw AssertionError(""""OkHttp failed with HTTP ${response.code()} ${response.message()}""")
        }

private fun truncate(string: String?): String = when {
    string == null -> ""
    string.length < 200 -> string
    else -> string.substring(0, 197) + "..."
}


