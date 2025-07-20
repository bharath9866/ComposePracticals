@file:OptIn(ExperimentalLayoutApi::class)

package com.example.adaptivestreamingplayer.gSmart

import android.R.attr.strokeWidth
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.R


@Preview
@Composable
fun FanIcon(
    isOn: Boolean = true,
    speed: String = "0",
    modifier: Modifier = Modifier
) {
    val shouldAnimate by remember(isOn, speed) {
        derivedStateOf { isOn && speed != "0" }
    }

    val animationDuration = remember(speed) {
        when (speed) {
            "1" -> 700
            "2" -> 500
            "3" -> 300
            "4" -> 100
            else -> 1000
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "fan_transition")

    val rotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = if (shouldAnimate) 360f else 0f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDuration,
                easing = LinearEasing
            )
        ),
        label = "fan_rotation"
    )

    val iconTint by remember(isOn) {
        derivedStateOf {
            if (isOn) Color.White else Color(0xFF9E9E9E)
        }
    }

    Icon(
        painter = painterResource(id = R.drawable.ic_fan),
        contentDescription = if (isOn) "Fan running at speed $speed" else "Fan off",
        modifier = modifier.rotate(rotation),
        tint = iconTint
    )
}

@Preview(backgroundColor = 0xFF000000, showBackground = true)
@Composable
private fun FanCardGradient() {
    Column(
        modifier = Modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        FanCard()
    }
}

@Preview
@Composable
private fun Rowing() {
    FlowRow(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF000A18)),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        val oneList = List(5) {
            Device(deviceStatus = "$it")
        }

        repeat(oneList.size) {
            FanCard(device = oneList[it])
        }

        val offList = List(5) {
            Device(deviceStatus = "$it")
        }

        repeat(oneList.size) {
            FanCard(device = offList[it])
        }
    }
}

@Preview
@Composable
private fun FanCard(
    device: Device = Device(),
    cornerRadius: Dp = 16.dp,
    strokeWidth: Dp = 5.dp,
    boxSize: Dp = 105.dp
) {
    val density = LocalDensity.current

    val isOn by remember(device.deviceStatus) {
        derivedStateOf { device.isDeviceOn }
    }

    val scale = remember(boxSize) {
        with(density) { boxSize.toPx() / 77f }
    }

    val statusText = remember(device.deviceStatus) {
        when (device.deviceStatus) {
            in listOf("1", "2", "3", "4", "5") -> "Speed ${device.deviceStatus}"
            else -> "Off"
        }
    }

    // Cache brushes to avoid recreation
    val activeBrushes = remember(scale) {
        Pair(
            Brush.linearGradient(
                colors = listOf(Color.White, Color(0xFF39C7FF)),
                start = Offset(5.11f * scale, 5.8f * scale),
                end = Offset(40.6f * scale, 43.74f * scale)
            ),
            Brush.linearGradient(
                colors = listOf(Color(0xFF39C7FF), Color(0xFF39C7FF)),
                start = Offset(4.51f * scale, 42.52f * scale),
                end = Offset(42.17f * scale, 4.86f * scale)
            )
        )
    }

    val inactiveBrush = remember(scale) {
        Brush.linearGradient(
            colors = listOf(Color(0xFF4A4A4A), Color(0xFF121212)),
            start = Offset(53f * scale, -151.94f * scale),
            end = Offset(53f * scale, 178.72f * scale)
        )
    }

    Box(
        modifier = Modifier
            .width(boxSize)
            .aspectRatio(0.7664f)
            .padding(strokeWidth / 2)
            .drawBehind {
                if (isOn) {
                    drawRoundRect(
                        brush = activeBrushes.first,
                        size = this.size,
                        cornerRadius = CornerRadius(cornerRadius.toPx()),
                        style = Stroke(width = strokeWidth.toPx())
                    )
                    drawRoundRect(
                        brush = activeBrushes.second,
                        size = this.size,
                        cornerRadius = CornerRadius(cornerRadius.toPx())
                    )
                } else {
                    drawRoundRect(
                        brush = inactiveBrush,
                        size = this.size,
                        cornerRadius = CornerRadius(cornerRadius.toPx())
                    )
                }
            }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            DeviceIconBackgroundCircle(
                circleSize = 50.dp,
                strokeWidth = 2.dp
            ) {
                FanIcon(
                    isOn = isOn,
                    speed = device.deviceStatus,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(4.dp)
                )
            }

            device.name?.let { name ->
                Text(
                    text = name,
                    modifier = Modifier.basicMarquee(),
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Text(
                text = statusText,
                modifier = Modifier.basicMarquee(),
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

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

sealed interface DeviceActions {
    data class OnPublish(val device: Device, val deviceStatus: String): DeviceActions
    data class FanSpeed(val device: Device, val speedNumber: String): DeviceActions
}

data class Device(
    val type: String? = "Fan",
    val index: String? = "0",
    val assign: String? = "0",
    val gCode: String? = null,
    val companyName: String? = null,
    val name: String? = "Fan",
    val action: String? = null,
    val irData: String? = null,
    val publishKey: String? = null,
    val subscribeKey: String? = null,
    val icon: String? = null,
    val onIconColor: String? = null,
    val offIconColor: String? = null,
    val deviceStatus: String = "0"
) {
    val isDeviceOn: Boolean
        get() = when(type?.lowercase()) {
            "light" -> deviceStatus == "1"
            "fan" -> when (deviceStatus) {
                "1", "2", "3", "4" -> true
                else -> false
            }
            else -> false
        }
}