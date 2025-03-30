@file:OptIn(ExperimentalStdlibApi::class)

package com.example.adaptivestreamingplayer.coroutines.coroutineContext.whatIsCoroutineContext

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.coroutines.coroutineContext

suspend fun queryDatabase() {
    val job = coroutineContext[Job]
    val name = coroutineContext[CoroutineName]
    val handler = coroutineContext[CoroutineExceptionHandler]
    val dispatcher = coroutineContext[CoroutineDispatcher]

    CoroutineScope(Dispatchers.Main + CoroutineName("queryDatabaseContext")).launch {
        Timber.d("insideJob: ${coroutineContext[Job]}")
        Timber.d("insideName: ${coroutineContext[CoroutineName]}")
        Timber.d("insideDispatcher: ${coroutineContext[CoroutineDispatcher]}")
    }
    Timber.d("Job: $job")
    Timber.d("Name: $name")
    Timber.d("Handler: $handler")
    Timber.d("Dispatcher: $dispatcher")
}