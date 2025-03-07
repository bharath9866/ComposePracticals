package com.example.adaptivestreamingplayer.coroutines.coroutineBasics.joinsAndDeferred

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.lifecycleScope
import com.example.adaptivestreamingplayer.ui.theme.JetLaggedTheme
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.system.measureTimeMillis

class AsyncAwaitInCoroutine : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Toast.makeText(this.applicationContext, "See information in Android Studio LogCat", Toast.LENGTH_SHORT).show()

        val job = lifecycleScope.launch {

            launch { Timber.d("Step 1") }

            val async1 = async {
                Timber.d("Step 2")
                delay(10000L)
                Timber.d("Job 1 finished")
            }

            Timber.d("Step 3")

            val async2 = async {
                Timber.d("Step 4")
                delay(5000L)
                Timber.d("Job 2 finished")
            }

            val async3 = async {
                Timber.d("Step 5")
                delay(1000L)
                Timber.d("Job 3 finished")
            }.await()

            Timber.d("Step 6")

            val timeMillis = measureTimeMillis {
                Timber.d("Step 7")
                //job3.join()
                Timber.d("Step 8")
                async2.await()
                Timber.d("Step 9")
                //job1.join()
                Timber.d("Step 10")
            }

            Timber.d("Step 11")
            Timber.d("Jobs took $timeMillis milliseconds")
            Timber.d("Step 12")
        }
        setContent {
            JetLaggedTheme {  }
        }
    }
}