package com.example.adaptivestreamingplayer.animation

import android.util.Log
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ChainingAnimation() {
    var scale by remember { mutableFloatStateOf(1f) }
    var firstCardAlpha by remember { mutableFloatStateOf(1f) }
    var secondCardAlpha by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableFloatStateOf(0f) }

    // Specify the key that should trigger the animation (e.g: when one part of your state changes)
    // If you keep Unit, the animation will run at the first time composition
    LaunchedEffect(key1 = Unit) {
        animate(initialValue = 1f, targetValue = 0.9f) { value: Float, _: Float ->
            scale = value
        }
        coroutineScope {
            val transformationAnimationSpec = tween<Float>(
                durationMillis = 1000,
                easing = FastOutSlowInEasing,
            )

            launch {
                repeat(5) {
                    // Decrease animation duration per cycle to accelerate.
                    // Not meant to be scalable as would need to be adjusted if cycle count would change
                    val translationDuration = when (it) {
                        0 -> 200
                        1 -> 200
                        2 -> 100
                        3 -> 50
                        4 -> 20
                        else -> 0
                    }

                    val translationAnimationSpec = tween<Float>(
                        durationMillis = translationDuration,
                        easing = FastOutLinearInEasing,
                    )

                    animate(
                        initialValue = 0f,
                        targetValue = 1f,
                        animationSpec = translationAnimationSpec
                    ) { value: Float, _: Float ->
                        offset = value
                    }
                    animate(
                        initialValue = 1f,
                        targetValue = 0f,
                        animationSpec = translationAnimationSpec
                    ) { value: Float, _: Float ->
                        offset = value
                    }
                }
            }
            launch {
                animate(
                    initialValue = 0.9f,
                    targetValue = 0.8f,
                    animationSpec = transformationAnimationSpec
                ) { value: Float, _: Float ->
                    scale = value
                }
            }
            launch {
                animate(
                    initialValue = 1f,
                    targetValue = 0.4f,
                    animationSpec = transformationAnimationSpec
                ) { value: Float, _: Float ->
                    firstCardAlpha = value
                    secondCardAlpha = value
                }
            }
        }

        firstCardAlpha = 0f
        secondCardAlpha = 1f // Assuming we want to hide second card depending of what the shuffle result is
        offset = 0.3f

        delay(300)

        coroutineScope {
            launch {
                animate(initialValue = 0.5f, targetValue = 1f) { value: Float, _: Float ->
                    offset = value
                }
            }
            launch {
                animate(initialValue = 0.8f, targetValue = 1f) { value: Float, _: Float ->
                    scale = value
                }
            }
        }
    }

    Log.d("scale", "$scale")
    Log.d("firstCardAlpha", "$firstCardAlpha")
    Log.d("secondCardAlpha", "$secondCardAlpha")
    Log.d("offset", "$offset")
}

@Preview
@Composable
private fun ChainingAnimationPreview() {
    ChainingAnimation()
}