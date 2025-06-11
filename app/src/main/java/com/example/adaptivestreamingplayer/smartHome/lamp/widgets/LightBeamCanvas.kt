package com.example.adaptivestreamingplayer.smartHome.lamp.widgets

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LightBeamCanvas(
    modifier: Modifier = Modifier,
    isVisible: Boolean = true,
    lightOpacity: Float = 0.6f,
    lightColor: Color = Color.White,
    topConeWidth : Float = 220f,
    bottomConeWidth : Float = 600f,
    coneHeight : Float = 500f
) {

    Canvas(
        modifier = modifier
    ) {
        if (isVisible) {
            val canvasWidth = size.width
            val centerX = canvasWidth / 2
            val topY = 0f
            val bottomY = coneHeight

            val path = Path().apply {
                moveTo(centerX - topConeWidth / 2, topY)
                lineTo(centerX + topConeWidth / 2, topY)
                lineTo(centerX + bottomConeWidth / 2, bottomY)
                lineTo(centerX - bottomConeWidth / 2, bottomY)
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
        topConeWidth = 280f,
        lightColor = Color.Red
    )
}