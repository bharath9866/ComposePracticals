@file:OptIn(ExperimentalStdlibApi::class)

package com.example.adaptivestreamingplayer.coroutines.coroutineContext.withContext

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import kotlin.concurrent.timer
import kotlin.coroutines.coroutineContext

suspend fun withCoroutineDemo() {
    Timber.d("LaunchedEffectFromCompose-Main-Thread: ${Thread.currentThread().name}")
    withContext(Dispatchers.IO) {
        Timber.d("IO-Thread: ${Thread.currentThread().name}")
        withContext(Dispatchers.Main) {
            Timber.d("Main-Thread: ${Thread.currentThread().name}")
            withContext(Dispatchers.Default) {
                Timber.d("Default-Thread: ${Thread.currentThread().name}")
                withContext(Dispatchers.Unconfined) {
                    Timber.d("Unconfined-Thread: ${Thread.currentThread().name}")
                }
            }
        }
    }
}