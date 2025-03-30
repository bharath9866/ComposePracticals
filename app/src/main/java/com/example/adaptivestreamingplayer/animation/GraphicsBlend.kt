package com.example.adaptivestreamingplayer.animation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.layer.GraphicsLayer
import androidx.compose.ui.graphics.layer.drawLayer
import androidx.compose.ui.graphics.rememberGraphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.adaptivestreamingplayer.R

@Preview
@Composable
fun GraphicsLayerBlendModes() {
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.ic_add),
            contentDescription = null
        )
        val graphicsLayer2 = rememberGraphicsLayer()
        Image(
            painter = painterResource(id = R.drawable.ic_like),
            modifier = Modifier
                .blendLayer(graphicsLayer2, BlendMode.Lighten),
            contentDescription = null
        )
        val graphicsLayer3 = rememberGraphicsLayer()
        Image(
            painter = painterResource(id = R.drawable.avatar_1),
            modifier = Modifier
                .blendLayer(graphicsLayer3, BlendMode.Screen),
            contentDescription = null
        )
    }
}

fun Modifier.blendLayer(layer: GraphicsLayer, blendMode: BlendMode): Modifier {
    return this.drawWithContent {
        layer.apply {
            record {
                this@drawWithContent.drawContent()
            }
            this.blendMode = blendMode
        }
        drawLayer(layer)
    }
}