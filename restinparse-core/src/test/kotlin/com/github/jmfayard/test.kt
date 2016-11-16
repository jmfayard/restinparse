package com.github.jmfayard

import io.kotlintest.specs.StringSpec

class StringSpecExample : StringSpec() {
    init {
        "hello world" {
            RestInParse.GREETING shouldBe "Hello World!"
        }
    }
}

