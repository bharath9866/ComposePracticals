package com.example.adaptivestreamingplayer.coroutines.coroutineContext.withContext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.lifecycleScope

@Composable
fun WithCoroutineContextScreen() {
    LaunchedEffect(Unit) {
        withCoroutineDemo()
    }
}