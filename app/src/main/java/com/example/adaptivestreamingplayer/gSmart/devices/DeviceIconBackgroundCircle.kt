package com.example.adaptivestreamingplayer.gSmart.devices

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun DeviceIconBackgroundCircle(
    modifier: Modifier = Modifier,
    circleSize: Dp = 50.dp,
    strokeWidth: Dp = 2.dp,
    content: @Composable BoxScope.() -> Unit = {},
) {
    val strokePx = with(LocalDensity.current) { strokeWidth.toPx() }

    Box(
        modifier = modifier
            .size(circleSize)
            .padding(strokeWidth / 2)
            .drawBehind {
                val scale = size.minDimension / 90f
                val cornerRadius = CornerRadius(size.minDimension / 2)

                // Stroke outline
                drawRoundRect(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0xFFFFFFFF), Color(0x00FFFFFF)),
                        start = Offset(6.35f * scale, 7.08f * scale),
                        end = Offset(81.58f * scale, 87.52f * scale)
                    ),
                    size = size,
                    cornerRadius = cornerRadius,
                    style = Stroke(width = strokePx)
                )

                // Fill gradient
                drawRoundRect(
                    brush = Brush.linearGradient(
                        colors = listOf(Color(0x33FFFFFF), Color(0x7DFFFFFF)),
                        start = Offset(5.08f * scale, 84.92f * scale),
                        end = Offset(84.92f * scale, 5.08f * scale)
                    ),
                    size = size,
                    cornerRadius = cornerRadius
                )
            },
        contentAlignment = Alignment.Center
    ) {
        content()
    }
}