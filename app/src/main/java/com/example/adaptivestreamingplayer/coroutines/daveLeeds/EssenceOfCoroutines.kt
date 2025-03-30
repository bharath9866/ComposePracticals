package com.example.adaptivestreamingplayer.coroutines.daveLeeds

import kotlin.coroutines.Continuation
import kotlin.coroutines.EmptyCoroutineContext
import kotlin.coroutines.createCoroutine
import kotlin.coroutines.suspendCoroutine
import kotlin.coroutines.intrinsics.COROUTINE_SUSPENDED
import kotlin.coroutines.resume

fun main() {
    var cow = 0
    println("Milking cow #${cow++}"); feedChicken.resume(Unit)
    println("Milking cow #${cow++}"); feedChicken.resume(Unit)
    println("Milking cow #${cow++}"); feedChicken.resume(Unit)
    println("Milking cow #${++cow}"); feedChicken.resume(Unit)
}

val feedChicken = suspend {
    var chicken = 0
    println("Feeding chicken #${chicken++}"); yield()
    println("Feeding chicken #${chicken++}"); yield()
    println("Feeding chicken #${chicken++}"); yield()
    println("Feeding chicken #${++chicken}"); complete()
}.createCoroutine(Continuation(EmptyCoroutineContext) {})

suspend fun complete() = suspendCoroutine { it.resume(Unit) }

suspend fun yield() = suspendCoroutine<Unit> { COROUTINE_SUSPENDED }