package com.example.adaptivestreamingplayer.canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun LightModeInCompose() {
    Canvas(
        modifier = Modifier.size(350.dp)
    ) {
        // Draw the inner circle (sun)
        val path = Path().apply {
            moveTo(480f, 600f)
            quadraticTo(530f, 600f, 565f, 565f) // First curve
            quadraticTo(600f, 530f, 600f, 480f) // Second curve
            quadraticTo(600f, 430f, 565f, 395f) // Third curve
            quadraticTo(530f, 360f, 480f, 360f) // Fourth curve
            quadraticTo(430f, 360f, 395f, 395f) // Fifth curve
            quadraticTo(360f, 430f, 360f, 480f) // Sixth curve
            quadraticTo(360f, 530f, 395f, 565f) // Seventh curve
            quadraticTo(430f, 600f, 480f, 600f) // Closing curve

            // Move to the outer circle
            moveTo(480f, 680f)
            quadraticTo(397f, 680f, 338.5f, 621.5f)
            quadraticTo(280f, 563f, 280f, 480f)
            quadraticTo(280f, 397f, 338.5f, 338.5f)
            quadraticTo(397f, 280f, 480f, 280f)
            quadraticTo(563f, 280f, 621.5f, 338.5f)
            quadraticTo(680f, 397f, 680f, 480f)
            quadraticTo(680f, 563f, 621.5f, 621.5f)
            quadraticTo(563f, 680f, 480f, 680f)
        }

        // Draw the sun circle
        drawPath(
            path = path,
            color = Color.Red,
            style = Fill
        )

        // Draw the horizontal line (left and right of the sun)
        drawLine(
            color = Color.Black,
            start = Offset(40f, 480f),
            end = Offset(200f, 480f),
            strokeWidth = 10f
        )
        drawLine(
            color = Color.Black,
            start = Offset(760f, 480f),
            end = Offset(920f, 480f),
            strokeWidth = 10f
        )

        // Draw the vertical line (top and bottom of the sun)
        drawLine(
            color = Color.Black,
            start = Offset(480f, 40f),
            end = Offset(480f, 200f),
            strokeWidth = 10f
        )
        drawLine(
            color = Color.Black,
            start = Offset(480f, 760f),
            end = Offset(480f, 920f),
            strokeWidth = 10f
        )

        // Draw the diagonal lines
        drawLine(
            color = Color.Black,
            start = Offset(212f, 212f),
            end = Offset(310f, 310f),
            strokeWidth = 10f
        )
        drawLine(
            color = Color.Black,
            start = Offset(650f, 650f),
            end = Offset(748f, 748f),
            strokeWidth = 10f
        )
        drawLine(
            color = Color.Black,
            start = Offset(748f, 212f),
            end = Offset(650f, 310f),
            strokeWidth = 10f
        )
        drawLine(
            color = Color.Black,
            start = Offset(310f, 650f),
            end = Offset(212f, 748f),
            strokeWidth = 10f
        )
    }
}