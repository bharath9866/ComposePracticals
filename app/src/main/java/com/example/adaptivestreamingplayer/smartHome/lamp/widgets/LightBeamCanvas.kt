package com.example.adaptivestreamingplayer.smartHome.lamp.widgets

import android.R.attr.centerX
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LightBeamCanvas(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    lightOpacity: Float = 0.6f,
    lightColor: Color = Color.White,
    topConeWidth: Dp = 100.dp,
    bottomConeWidth: Dp = 320.dp,
    coneHeight: Dp = 250.dp
) {
    val density = LocalDensity.current

    Canvas(modifier = modifier) {
        if (isVisible) {
            // Convert Dp → Px based on screen density
            val topWidthPx = with(density) { topConeWidth.toPx() }
            val bottomWidthPx = with(density) { bottomConeWidth.toPx() }
            val coneHeightPx = with(density) { coneHeight.toPx() }

            val canvasWidth = size.width
            val centerX = canvasWidth / 2f

            val topY = 0f
            val bottomY = coneHeightPx

            val path = Path().apply {
                moveTo(centerX - topWidthPx / 2f, topY)
                lineTo(centerX + topWidthPx / 2f, topY)
                lineTo(centerX + bottomWidthPx / 2f, bottomY)
                lineTo(centerX - bottomWidthPx / 2f, bottomY)
                close()
            }

            drawPath(
                path = path,
                brush = Brush.verticalGradient(
                    colors = listOf(
                        lightColor.copy(alpha = lightOpacity),
                        Color.Transparent
                    ),
                    startY = topY,
                    endY = bottomY
                )
            )
        }
    }
}

@Preview(device = "spec:width=1280dp,height=800dp,dpi=240")
@Composable
private fun LightBeamCanvasPreview() {
    LightBeamCanvas(
        modifier = Modifier
            .padding(top = 180.dp)
            .offset(x = (-25).dp)
            .size(
                width = 200.dp,
                height = 300.dp
            ),
        isVisible = true,
        lightOpacity = 0.5f,
        topConeWidth = 120.dp,
        lightColor = Color.Red
    )
}