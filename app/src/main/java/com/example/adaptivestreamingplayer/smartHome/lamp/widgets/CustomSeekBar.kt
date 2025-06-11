package com.example.adaptivestreamingplayer.smartHome.lamp.widgets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp


@Composable
fun CustomSeekBar(
    modifier: Modifier = Modifier,
    progress: Float,
    onProgressChanged: (Float) -> Unit
) {
    val stepCount = 10
    val steps = List(stepCount) { it * 100f / (stepCount - 1) }
    val progressState = remember { mutableStateOf(progress) }
    val dragOffset = remember { mutableStateOf(0f) }

    val localDensity = LocalDensity.current

    Box(modifier = modifier) {
        Canvas(modifier = Modifier.fillMaxWidth().height(50.dp).pointerInput(Unit) {
            detectHorizontalDragGestures { _, dragAmount ->
                val newOffset = dragOffset.value + dragAmount
                dragOffset.value = newOffset.coerceIn(0f, size.width.toFloat())
                progressState.value = dragOffset.value / size.width
                onProgressChanged(progressState.value)
            }
        }) {
            val width = size.width
            val height = size.height
            val progressBarHeight = with(localDensity) { 50.dp.toPx() }
            val stepHeight = with(localDensity) { 10.dp.toPx() }
            val cornerRadius = with(localDensity) { 10.dp.toPx() }
            val progressWidth = progressState.value * width

            drawRoundRect(
                color = Color.Black.copy(alpha = 0.5f),
                size = Size(width, progressBarHeight),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )

            drawRoundRect(
                color = Color.Black,
                size = Size(progressWidth, progressBarHeight),
                cornerRadius = CornerRadius(cornerRadius, cornerRadius)
            )

            steps.forEachIndexed { index, step ->
                val x = width * step / 100f
                if (index != 0 && index != steps.lastIndex) {
                    drawLine(
                        color = Color.White,
                        start = Offset(x, (height - stepHeight) / 2),
                        end = Offset(x, (height + stepHeight) / 2),
                        strokeWidth = 2f
                    )
                }
            }
        }
    }
}