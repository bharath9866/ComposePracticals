package com.example.adaptivestreamingplayer.animation

import androidx.compose.animation.core.animateIntOffset
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset

enum class Location {
    HOME,
    WORK,
    CINEMA
}

@Preview
@Composable
private fun UpdateTransitionSample() {
    val currentState by remember { mutableStateOf(Location.HOME) }
    val transition = updateTransition(targetState = currentState, label = "transition")

    val personOffset by transition.animateIntOffset(
        transitionSpec = {
            when {
                Location.WORK isTransitioningTo Location.HOME -> tween(500)
                Location.CINEMA isTransitioningTo Location.HOME -> tween(4000)
                else -> tween(2000)
            }
        },
        label = "personOffset"
    ) { targetLocation ->
        when (targetLocation) {
            Location.HOME -> IntOffset.Zero
            Location.WORK -> IntOffset.Zero
            Location.CINEMA -> IntOffset.Zero
        }
    }
}