package com.example.adaptivestreamingplayer.coroutines.coroutineBasics.launchingYourFirstCoroutines

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.adaptivestreamingplayer.ui.theme.JetLaggedTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LaunchYourFirstCoroutine : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        GlobalScope.launch {
            repeat(100) {
                println("Hello from coroutine1")
            }
        }
        GlobalScope.launch {
            repeat(100) {
                println("Hello from coroutine2")
            }
        }
        setContent {
            JetLaggedTheme {

            }
        }
    }
}