package com.example.adaptivestreamingplayer

import android.util.Log
import timber.log.Timber
import kotlin.system.measureTimeMillis

fun main() {

    fun benchmark(example: Example) {
        val iterations = 1_000_000
        for (i in 0 until iterations) {
            example.start++
            example.end++
        }
    }

    val timeInMillis = measureTimeMillis {
        benchmark(Example())
    }
    println(timeInMillis)
}

class Example {
    var start: Int = 0  // Alphabetically first
    var end: Int = 0    // Alphabetically second
}