package com.example.adaptivestreamingplayer.kotlinTips

fun main() {
    runCatching {
        1/0
    }.onSuccess { println(it) }
        .onFailure { println(it.message) }
}