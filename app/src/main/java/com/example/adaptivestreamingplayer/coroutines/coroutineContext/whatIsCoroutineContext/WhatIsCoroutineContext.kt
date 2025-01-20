package com.example.adaptivestreamingplayer.coroutines.coroutineContext.whatIsCoroutineContext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun WhatIsCoroutineContext() {
    LaunchedEffect(Unit) {
        queryDatabase()
    }
}