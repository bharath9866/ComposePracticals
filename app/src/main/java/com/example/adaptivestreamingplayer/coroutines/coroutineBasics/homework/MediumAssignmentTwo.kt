package com.example.adaptivestreamingplayer.coroutines.coroutineBasics.homework

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.system.measureTimeMillis

@Composable
fun MediumAssignmentTwo() {
    LaunchedEffect(Unit) {
        Timber.d("See information in Android Studio LogCat")
        val birdOne = launch {
            var counter = 0
            while (true) {
                Timber.d("Bird One: Coo-${++counter}")
                delay(1000L)
            }
        }
        val birdTwo = launch {
            var counter = 0
            while (true) {
                Timber.d("Bird Two: Caw-${++counter}")
                delay(2000L)
            }
        }
        val birdThree = launch {
            var counter = 0
            while (true) {
                Timber.d("Bird Three: Chirp-${++counter}")
                delay(3000L)
            }
        }
        val timeInMillis = measureTimeMillis {
            delay(10000)
            this.cancel()
        }
        Timber.d("Time Taken: $timeInMillis")
    }
}