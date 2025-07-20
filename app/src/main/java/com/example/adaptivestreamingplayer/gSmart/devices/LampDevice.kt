package com.example.adaptivestreamingplayer.gSmart.devices

import android.R.attr.iconTint
import android.R.attr.rotation
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.gSmart.Device
import com.example.adaptivestreamingplayer.gSmart.DeviceActions

@Composable
fun LampCard(
    device: Device,
    cornerRadius: Dp,
    strokeWidth: Dp,
    boxWidth: Dp,
    onSelectedDevice: (DeviceActions) -> Unit,
) {
    val density = LocalDensity.current
    val isOn by remember(device.deviceStatus) {
        derivedStateOf { device.isDeviceOn }
    }

    val iconTint by remember(isOn) {
        derivedStateOf {
            if (isOn) Color.White else Color(0xFF9E9E9E)
        }
    }

    val scale = remember(boxWidth) {
        with(density) { boxWidth.toPx() / 77f }
    }

    val statusText by remember(device.deviceStatus) {
        derivedStateOf {
            if(device.deviceStatus == "1") "On" else "Off"
        }
    }

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
            .width(boxWidth)
            .aspectRatio(0.7664f)
            .padding(strokeWidth / 2)
            .clickable {
                onSelectedDevice(
                    DeviceActions.OnPublish(device, device.deviceStatus)
                )
            }
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
                Icon(
                    painter = painterResource(R.drawable.ic_bulb),
                    contentDescription = if (isOn) "Lamp On" else "Lamp off",
                    tint = iconTint,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
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
fun LampCardPreview() {
    LampCard(
        device = Device(
            type = "Light",
            name = "Light",
            deviceStatus = "1"
        ),
        cornerRadius = 16.dp,
        strokeWidth = 5.dp,
        boxWidth = 105.dp,
        onSelectedDevice = {},
    )
}