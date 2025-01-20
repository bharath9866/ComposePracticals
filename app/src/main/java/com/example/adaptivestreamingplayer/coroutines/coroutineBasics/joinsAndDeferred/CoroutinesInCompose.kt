package com.example.adaptivestreamingplayer.coroutines.coroutineBasics.joinsAndDeferred

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun CoroutineInComposeScreen(modifier: Modifier = Modifier) {
    val snackBarState = remember { SnackbarHostState() }
    var isSnackBarShowing by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(isSnackBarShowing) { Timber.d("$isSnackBarShowing") }

    Scaffold(
        snackbarHost = { SnackbarHost(snackBarState) },
    ) { innerPadding ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    scope.launch {
                        isSnackBarShowing = true
                        snackBarState.showSnackbar("Hello World!")
                        isSnackBarShowing = false
                    }
                }
            ) {
                Text(text = if(isSnackBarShowing) "Snackbar is showing" else "Show Snackbar")
            }
        }
    }
}