package com.example.adaptivestreamingplayer.dsa

import java.util.Locale
import kotlin.math.max

fun <T, R> benchmark(
    description: String,
    input: T,
    block: (T) -> R
): R {
    println("[$description]")
//    println("Input: ${formatInput(input)}")

    // Warmup to stabilize JIT
    repeat(3) { block(input) }

    System.gc()
    Thread.sleep(50) // give GC some time

    val beforeMem = usedMemory()
    val start = System.nanoTime()

    val result = block(input)

    val end = System.nanoTime()
    val afterMem = usedMemory()

    val timeNs = end - start
    val memoryUsed = max(0, afterMem - beforeMem)

    println("Output: $result")
    println("Execution time: ${formatTime(timeNs)}")
    println("Memory used: ${formatMemory(memoryUsed)}")
    println("------------")

    return result
}

fun formatInput(input: Any?): String {
    return when (input) {
        is IntArray -> input.contentToString()
        is LongArray -> input.contentToString()
        is DoubleArray -> input.contentToString()
        is FloatArray -> input.contentToString()
        is BooleanArray -> input.contentToString()
        is Array<*> -> input.contentToString()
        else -> input.toString()
    }
}

fun usedMemory(): Long {
    val runtime = Runtime.getRuntime()
    return runtime.totalMemory() - runtime.freeMemory()
}

fun formatMemory(bytes: Long): String {
    return when {
        bytes < 1024 -> "$bytes B"
        bytes < 1024 * 1024 -> "${bytes / 1024} KB"
        else -> String.format(Locale.US, "%.2f MB", bytes.toDouble() / (1024 * 1024))
    }
}

fun formatTime(ns: Long): String {
    return when {
        ns < 1_000 -> "$ns ns"
        ns < 1_000_000 -> "${ns / 1_000} µs"
        ns < 1_000_000_000 -> "${ns / 1_000_000} ms"
        else -> String.format(Locale.US, "%.2f s", ns / 1_000_000_000.0)
    }
}
