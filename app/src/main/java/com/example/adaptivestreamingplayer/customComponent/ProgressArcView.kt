package com.example.adaptivestreamingplayer.customComponent

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun GaugeChart(score: Int, maxScore: Int) {
    val percentage = score.toFloat() / maxScore
    val angle = 180f * percentage

    Canvas(modifier = Modifier.fillMaxSize()) {
        drawGradientBackground()
        //drawHighlightedSection(angle)
        drawScoreText(score, maxScore)
    }
}

fun DrawScope.drawGradientBackground() {
    val colors = listOf(
        Color(0xFFEC9191),
        Color(0xFFFFB762),
        Color(0xFFFFD82A),
        Color(0xFF96D72C),
        Color(0xFF40B56C),
    )
    val gradient = Brush.horizontalGradient(colors)
    drawArc(gradient, 180f, 180f, useCenter = false, style = Stroke(30.dp.toPx()))
}

fun DrawScope.drawHighlightedSection(angle: Float) {
    drawArc(Color(0xFFFFA500), 180f, angle, useCenter = false, style = Stroke(30.dp.toPx()))
}

fun DrawScope.drawScoreText(score: Int, maxScore: Int) {
    drawContext.canvas.nativeCanvas.apply {
        drawText(
            /* text = */ "Score",
            /* x = */ size.width / 2,
            /* y = */ size.height / 2 - 10,
            /* paint = */ Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 40f
                textAlign = android.graphics.Paint.Align.CENTER
            }
        )
        drawText(
            /* text = */ "$score/$maxScore",
            /* x = */ size.width / 2,
            /* y = */ size.height / 2 + 30,
            /* paint = */ Paint().apply {
                color = android.graphics.Color.BLACK
                textSize = 30f
                textAlign = android.graphics.Paint.Align.CENTER
            }
        )
    }
}

@Preview(backgroundColor = 0xFFFFFFFF, showBackground = true)
@Composable
fun PreviewGaugeChart() {
    GaugeChart(score = 75, maxScore = 300)
}