package com.example.adaptivestreamingplayer.coroutines.coroutineBasics.joinsAndDeferred

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.awaitCancellation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

class ConcurrenceBasicIntroduction : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            launch {
                launch {
                    Timber.d("Innermost coroutine finished - 1")
                    delay(1000L)
                    Timber.d("Innermost coroutine finished - 2")
                }
                Timber.d("Inner coroutine finished - 1")
                delay(500L)
                Timber.d("Inner coroutine finished - 2")
            }
            Timber.d("Outermost coroutine finished!") // Step : 1
        }

    }
}