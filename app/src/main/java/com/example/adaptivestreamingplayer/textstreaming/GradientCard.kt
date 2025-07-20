package com.example.adaptivestreamingplayer.textstreaming

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun GradientCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 12.dp,
    borderWidth: Dp = 1.dp,
    content: @Composable () -> Unit
) {
    val backgroundGradient = Brush.linearGradient(
        colors = listOf(
            Color(0xFF39C7FF), // Start color
            Color(0xFF39C7FF)  // End color (same as start in your XML)
        ),
        start = Offset(4.51f, 42.52f),
        end = Offset(42.17f, 4.86f)
    )

    val borderGradient = Brush.linearGradient(
        colors = listOf(
            Color.White,        // Start color
            Color(0xFF39C7FF)   // End color
        ),
        start = Offset(5.11f, 5.8f),
        end = Offset(40.6f, 43.74f)
    )

    val shape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .clip(shape)
            .border(borderWidth, borderGradient, shape)
            .background(backgroundGradient, shape)
    ) {
        content()
    }
}

// Alternative version with more customization options
@Composable
fun CustomGradientCard(
    modifier: Modifier = Modifier,
    cornerRadius: Dp = 12.dp,
    borderWidth: Dp = 2.dp,
    backgroundColors: List<Color> = listOf(Color(0xFF39C7FF), Color(0xFF39C7FF)),
    borderColors: List<Color> = listOf(Color.White, Color(0xFF39C7FF)),
    backgroundStart: Offset = Offset(4.51f, 42.52f),
    backgroundEnd: Offset = Offset(42.17f, 4.86f),
    borderStart: Offset = Offset(5.11f, 5.8f),
    borderEnd: Offset = Offset(40.6f, 43.74f),
    content: @Composable () -> Unit
) {
    val backgroundGradient = Brush.linearGradient(
        colors = backgroundColors,
        start = backgroundStart,
        end = backgroundEnd
    )

    val borderGradient = Brush.linearGradient(
        colors = borderColors,
        start = borderStart,
        end = borderEnd
    )

    val shape = RoundedCornerShape(cornerRadius)

    Box(
        modifier = modifier
            .clip(shape)
            .border(borderWidth, borderGradient, shape)
            .background(backgroundGradient, shape)
    ) {
        content()
    }
}