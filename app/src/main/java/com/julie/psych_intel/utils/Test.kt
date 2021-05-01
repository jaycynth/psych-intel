package com.julie.psych_intel.utils

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

fun foo(): Flow<String> = flowOf("a","b","c").map { it.plus("jan") }

fun main() = runBlocking {
    val values = foo()
    values.collect { x -> println(x) }
}