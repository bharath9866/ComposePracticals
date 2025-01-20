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
import kotlin.system.measureTimeMillis

class AsyncAwaitInCoroutine : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        Toast.makeText(this.applicationContext, "See information in Android Studio LogCat", Toast.LENGTH_SHORT).show()

        val job = lifecycleScope.launch {

            launch { println("Step 1") }

            val async1 = async {
                println("Step 2")
                delay(10000L)
                println("Job 1 finished")
            }

            println("Step 3")

            val async2 = async {
                println("Step 4")
                delay(5000L)
                println("Job 2 finished")
            }

            val async3 = async {
                println("Step 5")
                delay(1000L)
                println("Job 3 finished")
            }.await()

            println("Step 6")

            val timeMillis = measureTimeMillis {
                println("Step 7")
                //job3.join()
                println("Step 8")
                async2.await()
                println("Step 9")
                //job1.join()
                println("Step 10")
            }

            println("Step 11")
            println("Jobs took $timeMillis milliseconds")
            println("Step 12")
        }
        setContent {
            JetLaggedTheme {  }
        }
    }
}