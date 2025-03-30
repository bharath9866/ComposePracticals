package com.example.adaptivestreamingplayer.coroutines.coroutineBasics.homework

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.delay
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.system.measureTimeMillis

@Composable
fun EasyAssignmentOne() {
    LaunchedEffect(Unit) {
        Timber.d("See information in Android Studio LogCat")
        val birdOne = launch {
            repeat(4) {
                Timber.d("Bird One: Coo-${it + 1}")
                delay(1000L)
            }
        }
        val birdTwo = launch {
            repeat(4) {
                Timber.d("Bird Two: Caw-${it + 1}")
                delay(2000L)
            }
        }
        val birdThree = launch {
            repeat(4) {
                Timber.d("Bird Three: Chirp-${it + 1}")
                delay(3000L)
            }
        }
        val timeInMillis = measureTimeMillis {
            joinAll(birdOne, birdTwo, birdThree)
        }
        Timber.d("Time Taken: $timeInMillis")
    }
}