package com.example.adaptivestreamingplayer.coroutines.coroutineContext.homework

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.*
import timber.log.Timber

@Preview
@Composable
fun EasyAssignmentOneCoroutineContext() {
    var selectedBird: Bird? by remember { mutableStateOf(null) }

    LaunchedEffect(key1 = selectedBird) {
        withContext(Dispatchers.Unconfined+CoroutineName(selectedBird?.name?:"")) {
            selectedBird?.let {
                while (isActive) {
                    Timber.d("Sound: ${it.sound}, Name: ${coroutineContext[CoroutineName]?.name}")
                    delay(it.duration)
                    selectedBird = null
                    cancel()
                }
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            selectedBird?.sound?.let { Text(it) }

            birds.forEach { bird ->
                BirdButton(
                    sound = bird.sound,
                    onClick = { selectedBird = bird }
                )
            }
        }
    }
}

@Composable
fun BirdButton(
    sound: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        colors = ButtonColors(
            containerColor = Color.Black,
            contentColor = Color.White,
            disabledContentColor = Color.Transparent,
            disabledContainerColor = Color.Gray
        )

    ) {
        Text(
            text = sound,
            color = Color.White
        )
    }
}

data class Bird(
    val sound: String,
    val name:String,
    val duration: Long
)

val birds = listOf(
    Bird(sound = "Coo", name = "Tweety", duration = 1000L),
    Bird(sound = "Caw", name = "Zazu", duration = 2000L),
    Bird(sound = "Chirp", name = "Woodstock", duration = 3000L)
)