package com.github.jmfayard

import okio.BufferedSink
import okio.BufferedSource
import okio.Okio
import java.io.File

fun <T> T.debug(name: String): T {
    println("DEBUG: ${name} = ${toString()}")
    return this
}

fun BufferedSink.newLine() = writeUtf8("\n")


fun File.okSource(): BufferedSource = Okio.buffer(Okio.source(this))

fun File.okSink(): BufferedSink = Okio.buffer(Okio.sink(this))

fun File.okAppendingSink(): BufferedSink = Okio.buffer(Okio.appendingSink(this))


