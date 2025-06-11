package com.example.adaptivestreamingplayer.smartHome.lamp.widgets

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.sin

@Composable
fun RealisticRopeLightSwitch(
    modifier: Modifier = Modifier,
    ropeColor: Color = Color.Gray,
    handleColor: Color = Color.Red,
    onLightSwitch: (Boolean) -> Unit
) {
    val scope = rememberCoroutineScope()
    val lineTop = remember { Animatable(0f) }
    val lineBottom = 400f
    val handleRadius = with(LocalDensity.current) { 20.dp.toPx() }
    val innerHandleRadius = handleRadius * 0.5f
    val handlerOffsetY = remember { Animatable(lineBottom) }
    val shakeAnimation = remember { Animatable(0f) }
    var isLightOn by remember { mutableStateOf(false) }
    var isPulledDown by remember { mutableStateOf(false) }

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { _, dragAmount ->
                            scope.launch {
                                val newOffsetY = handlerOffsetY.value + dragAmount.y
                                if (newOffsetY in 0f..(lineBottom + handleRadius * 7.5f)) {
                                    handlerOffsetY.snapTo(newOffsetY)
                                }
                            }
                        },
                        onDragEnd = {
                            scope.launch {
                                if (handlerOffsetY.value > lineBottom + 50f) {
                                    isLightOn = !isLightOn
                                    isPulledDown = true
                                    onLightSwitch(isLightOn)
                                    shakeAnimation.animateTo(
                                        targetValue = 20f,
                                        animationSpec = repeatable(
                                            iterations = 6,
                                            animation = tween(durationMillis = 100),
                                            repeatMode = RepeatMode.Reverse
                                        )
                                    )
                                    shakeAnimation.snapTo(0f)
                                    isPulledDown = false
                                }

                                handlerOffsetY.animateTo(
                                    targetValue = lineBottom,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessHigh
                                    )
                                )

                                lineTop.animateTo(
                                    targetValue = -30f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )

                                lineTop.animateTo(
                                    targetValue = 0f,
                                    animationSpec = spring(
                                        dampingRatio = Spring.DampingRatioMediumBouncy,
                                        stiffness = Spring.StiffnessLow
                                    )
                                )
                            }
                        }
                    )
                }
        ) {
            val centerX = size.width / 2
            val horizontalShake = if (isPulledDown) shakeAnimation.value * sin(handlerOffsetY.value * 0.1f) else 0f
            val mouseShapeWidth = handleRadius * 2f
            val mouseShapeHeight = handleRadius * 2.5f

            drawLine(
                color = ropeColor,
                start = Offset(centerX, lineTop.value),
                end = Offset(centerX + horizontalShake, handlerOffsetY.value),
                strokeWidth = 8f
            )

            drawLine(
                color = handleColor,
                start = Offset(centerX, lineTop.value),
                end = Offset(centerX + horizontalShake, handlerOffsetY.value),
                strokeWidth = 12f
            )

            drawRoundRect(
                color = ropeColor,
                topLeft = Offset(
                    centerX - mouseShapeWidth / 2 + horizontalShake,
                    handlerOffsetY.value - mouseShapeHeight / 2
                ),
                size = Size(mouseShapeWidth, mouseShapeHeight),
                cornerRadius = CornerRadius(handleRadius, handleRadius),
                style = Stroke(width = 6f)
            )
            drawCircle(
                color = handleColor,
                center = Offset(centerX + horizontalShake, handlerOffsetY.value),
                radius = innerHandleRadius
            )
        }
    }
}

@Preview
@Composable
private fun RealisticRopeLightSwitchPreview() {
    var isLightOn by remember { mutableStateOf(true) }
    Box(
        modifier = Modifier.fillMaxSize().background(Color.White)
    ) {
        RealisticRopeLightSwitch(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .width(100.dp)
                .height(400.dp),
            ropeColor = Color.Gray,
            handleColor = Color.Black,
            onLightSwitch = { isOn ->
                isLightOn = isOn
            }
        )
    }
}