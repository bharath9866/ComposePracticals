package com.example.adaptivestreamingplayer.stroke

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun StrokeBrushHome(modifier: Modifier = Modifier) {
    StrokeBrush()
}
@Preview
@Composable
private fun StrokeBrush() {
    val side1 = 100
    val side2 = 200
    Canvas(modifier = canvasModifier) {
        drawLine(
            brush = Brush.radialGradient(
                0.0f to Color.Red,
                0.3f to Color.Green,
                1.0f to Color.Blue,
                center = Offset(side1 / 2.0f, side2 / 2.0f),
                radius = side1 / 2.0f,
                tileMode = TileMode.Repeated
            ),
            start = Offset(x = 100f, y = 30f),
            end = Offset(x = size.width - 100f, y = 30f),
            strokeWidth = 320f
        )
    }
}

private val canvasModifier = Modifier
    .shadow(1.dp)
    .background(Color.White)
    .fillMaxWidth()
    .height(60.dp)
