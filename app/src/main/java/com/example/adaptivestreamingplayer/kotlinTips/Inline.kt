package com.example.adaptivestreamingplayer.kotlinTips

fun main() {
    processRecords("Alice", "Billy", "Charlie", "Donald")
}

fun processRecords(vararg records: String) {
    for (record in records) {
        executeAndMeasure(record) {
            if(record.startsWith("C")) return@processRecords
            save(record)
        }
    }
}

inline fun executeAndMeasure(label: String, block: () -> Unit) {
    val start = System.nanoTime()
    block()
    val end = System.nanoTime()
    println("Duration for $label: ${(end - start) / 1_000_000} ms")
}

fun save(str: String) {
    println(str)
}