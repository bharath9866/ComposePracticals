package com.example.adaptivestreamingplayer.canvas

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import androidx.graphics.shapes.toPath
import com.example.adaptivestreamingplayer.R

@Preview
@Composable
fun ToolTip(modifier: Modifier = Modifier) {
    Column(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // SpeechBubbleShape
        Box(
            modifier = Modifier
                .width(200.dp)
                .height(100.dp)
                .clip(SpeechBubbleShape())
                .background(Color.Red),
            contentAlignment = Alignment.Center
        ) {
            Text("SpeechBubbleShape", color = Color.Blue, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        }

        // Polygons
        Box(
            modifier = Modifier
                .drawWithCache {
                    val roundedPolygon = RoundedPolygon(
                        numVertices = 6,
                        radius = size.minDimension / 2,
                        centerX = size.width / 2,
                        centerY = size.height / 2
                    )
                    val roundedPolygonPath = roundedPolygon.toPath().asComposePath()
                    onDrawBehind {
                        drawPath(roundedPolygonPath, color = Color.Blue)
                    }
                }
                .size(200.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("Polygons", color = Color.Red, fontSize = 25.sp, fontWeight = FontWeight.Bold)
        }

        val hexagon = remember {
            RoundedPolygon(
                6,
                rounding = CornerRounding(0.2f)
            )
        }
        val clip = remember(hexagon) {
            RoundedPolygonShape(polygon = hexagon)
        }
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.avatar_1),
                contentDescription = "Dog",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .graphicsLayer {
                        this.shadowElevation = 6.dp.toPx()
                        this.shape = clip
                        this.clip = true
                        this.ambientShadowColor = Color.Black
                        this.spotShadowColor = Color.Black
                    }
                    .size(200.dp)

            )
        }
    }
}

class SpeechBubbleShape(
    val cornerRadius: Dp = 15.dp, val tipSize: Dp = 15.dp
) : Shape {
    override fun createOutline(
        size: Size, layoutDirection: LayoutDirection, density: Density
    ): Outline {
        val cornerRadius = with(density) { cornerRadius.toPx() }
        val tipSize = with(density) { tipSize.toPx() }
        val path = Path().apply {
            addRoundRect(
                RoundRect(
                    left = tipSize,
                    top = 0f,
                    right = size.width,
                    bottom = size.height - tipSize,
                    radiusX = cornerRadius,
                    radiusY = cornerRadius
                )
            )
            moveTo(
                x = tipSize, y = size.height - tipSize - cornerRadius
            )
            lineTo(
                x = 0f, y = size.height
            )
            lineTo(
                x = tipSize + cornerRadius, y = size.height - tipSize
            )
            close()
        }
        return Outline.Generic(path)
    }
}

fun RoundedPolygon.getBounds() = calculateBounds().let { Rect(it[0], it[1], it[2], it[3]) }
class RoundedPolygonShape(
    private val polygon: RoundedPolygon,
    private var matrix: Matrix = Matrix()
) : Shape {
    private var path = Path()
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        path.rewind()
        path = polygon.toPath().asComposePath()
        matrix.reset()
        val bounds = polygon.getBounds()
        val maxDimension = maxOf(bounds.width, bounds.height)
        matrix.scale(size.width / maxDimension, size.height / maxDimension)
        matrix.translate(-bounds.left, -bounds.top)

        path.transform(matrix)
        return Outline.Generic(path)
    }
}