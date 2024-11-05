/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.adaptivestreamingplayer.jetlagged

import android.graphics.RuntimeShader
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.node.DrawModifierNode
import androidx.compose.ui.node.ModifierNodeElement
import com.example.adaptivestreamingplayer.ui.theme.White
import com.example.adaptivestreamingplayer.ui.theme.Yellow
import com.example.adaptivestreamingplayer.ui.theme.YellowVariant
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.Language

// *************************************** For Render Preview (Used only for Previews) ***************************************

private data object YellowBackgroundElement : ModifierNodeElement<YellowBackgroundNode>() {
    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun create() = YellowBackgroundNode()
    override fun update(node: YellowBackgroundNode) {
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private class YellowBackgroundNode : DrawModifierNode, Modifier.Node() {

    private val shader = RuntimeShader(SHADER)
    private val shaderBrush = ShaderBrush(shader)
    private val time = mutableFloatStateOf(0f)

    init {
        shader.setColorUniform(
            "color",
            android.graphics.Color.valueOf(Yellow.red, Yellow.green, Yellow.blue, Yellow.alpha)
        )
    }

    override fun ContentDrawScope.draw() {
        shader.setFloatUniform("resolution", size.width, size.height)
        shader.setFloatUniform("time", time.floatValue)
        drawRect(shaderBrush)
        drawContent()
    }

    override fun onAttach() {
        coroutineScope.launch {
            while (true) {
                withInfiniteAnimationFrameMillis {
                    time.floatValue = it / 1000f
                }
            }
        }
    }
}

fun Modifier.yellowBackground(): Modifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        this.then(YellowBackgroundElement)
    else
        this.simpleGradient()

// *************************************** For Dynamic Color Shades ***************************************

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private data class MovingStripesBackgroundElement(
    val stripeColor: Color,
    val backgroundColor: Color
) : ModifierNodeElement<MovingStripesBackgroundNode>() {
    override fun create() = MovingStripesBackgroundNode(stripeColor, backgroundColor)
    override fun update(node: MovingStripesBackgroundNode) {
        node.updateColors(stripeColor, backgroundColor)
    }
}

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
private class MovingStripesBackgroundNode(
    stripeColor: Color,
    backgroundColor: Color
) : DrawModifierNode, Modifier.Node() {

    private val shader = RuntimeShader(SHADER)
    private val shaderBrush = ShaderBrush(shader)
    private val time = mutableFloatStateOf(0f)

    init {
        updateColors(stripeColor, backgroundColor)
    }

    fun updateColors(stripeColor: Color, backgroundColor: Color) {
        shader.setColorUniform(
            "stripeColor", android.graphics.Color.valueOf(
                stripeColor.red,
                stripeColor.green,
                stripeColor.blue,
                stripeColor.alpha,
            )
        )
        shader.setFloatUniform(
            "backgroundLuminance", backgroundColor.luminance()
        )
        shader.setColorUniform(
            "backgroundColor",
            android.graphics.Color.valueOf(
                backgroundColor.red,
                backgroundColor.green,
                backgroundColor.blue,
                backgroundColor.alpha
            )
        )
    }

    override fun ContentDrawScope.draw() {
        shader.setFloatUniform("resolution", size.width, size.height)
        shader.setFloatUniform("time", time.floatValue)
        drawRect(shaderBrush)
        drawContent()
    }

    override fun onAttach() {
        coroutineScope.launch {
            while (true) {
                withInfiniteAnimationFrameMillis {
                    time.floatValue = it / 1000f
                }
            }
        }
    }
}

fun Modifier.movingStripesBackground(
    stripeColor: Color,
    backgroundColor: Color,
): Modifier = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        this.then(MovingStripesBackgroundElement(stripeColor, backgroundColor))
    else
        this.simpleGradient()

fun Modifier.simpleGradient(): Modifier = drawWithCache {
        val gradientBrush = Brush.verticalGradient(listOf(Yellow, YellowVariant, White))
        onDrawBehind { drawRect(gradientBrush,  alpha = 1f) }
    }

@Language("AGSL")
val SHADER = """
    uniform float2 resolution;
    uniform float time;
    layout(color) uniform half4 color;
    
    float calculateColorMultiplier(float yCoord, float factor) {
        return step(yCoord, 1.0 + factor * 2.0) - step(yCoord, factor - 0.1);
    }

    float4 main(in float2 fragCoord) {
        // Config values
        const float speedMultiplier = 1.5;
        const float waveDensity = 1.0;
        const float loops = 8.0;
        const float energy = 0.6;
        
        // Calculated values
        float2 uv = fragCoord / resolution.xy;
        float3 rgbColor = color.rgb;
        float timeOffset = time * speedMultiplier;
        float hAdjustment = uv.x * 4.3;
        float3 loopColor = vec3(1.0 - rgbColor.r, 1.0 - rgbColor.g, 1.0 - rgbColor.b) / loops;
        
        for (float i = 1.0; i <= loops; i += 1.0) {
            float loopFactor = i * 0.1;
            float sinInput = (timeOffset + hAdjustment) * energy;
            float curve = sin(sinInput) * (1.0 - loopFactor) * 0.05;
            float colorMultiplier = calculateColorMultiplier(uv.y, loopFactor);
            rgbColor += loopColor * colorMultiplier;
            
            // Offset for next loop
            uv.y += curve;
        }
        
        return float4(rgbColor, 1.0);
    }
""".trimIndent()
