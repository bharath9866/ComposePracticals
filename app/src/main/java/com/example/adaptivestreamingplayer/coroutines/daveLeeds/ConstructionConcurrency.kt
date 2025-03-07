package com.example.adaptivestreamingplayer.coroutines.daveLeeds

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {
    println("milliseconds without concurrency: ${synchronousCoroutineExecution()})")
    println("milliseconds with concurrency: ${withConcurrency()})")
    println("milliseconds with parallel execution: ${parallelCoroutineExecution()})")
    println("milliseconds with partialCancellation: ${partialCancellation()})")
}

fun partialCancellation() = measureTimeMillis {
    runBlocking {
        val window = async(Dispatchers.IO) { order(Product.WINDOWS) }
        val doors = async(Dispatchers.IO) { order(Product.DOORS) }.also { it.cancel() }

        launch(Dispatchers.Default) {
            perform("Laying bricks")
            launch { perform("installing ${window.await().description}") }
            launch { perform("installing ${doors.await().description}") }
        }
    }
}


fun parallelCoroutineExecution() = measureTimeMillis {
    runBlocking {
        val window = async(Dispatchers.IO) { order(Product.WINDOWS) }
        val doors = async(Dispatchers.IO) { order(Product.DOORS) }

        launch(Dispatchers.Default) {
            perform("Laying bricks")
            launch { perform("installing ${window.await().description}") }
            launch { perform("installing ${doors.await().description}") }
        }
    }
}


fun withConcurrency() = measureTimeMillis {
    runBlocking {
        val window = async { order(Product.WINDOWS) }
        val doors = async { order(Product.DOORS) }

        launch {
            perform("Laying bricks")
            perform("installing ${window.await().description}")
            perform("installing ${doors.await().description}")
        }
    }
}

fun synchronousCoroutineExecution() = measureTimeMillis {
    runBlocking {
        val window = order(Product.WINDOWS)
        val doors = order(Product.DOORS)

        perform("Laying bricks")
        perform("installing ${window.description}")
        perform("installing ${doors.description}")
    }
}

enum class Product(val description: String, val deliveryTime: Long) {
    DOORS("doors", 750),
    WINDOWS("windows", 1250)
}

suspend fun order(item: Product): Product {
    println("ORDER EN ROUTE >>> The ${item.description} are on the way!")
    delay(item.deliveryTime)
    println("ORDER DELIVERED >>> The ${item.description} are on the way!")
    return item
}

suspend fun perform(taskName: String) {
    println("STARTING TASK >>> $taskName")
    delay(1000)
    println("FINISHED TASK >>> $taskName")
}