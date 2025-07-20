@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.adaptivestreamingplayer.gSmart.devices

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.gSmart.Device
import com.example.adaptivestreamingplayer.gSmart.DeviceActions
import com.example.adaptivestreamingplayer.gSmart.onBoarding.onBoardingBottomSheet.BottomSheetBackgroundColor
import com.example.adaptivestreamingplayer.gSmart.onBoarding.onBoardingBottomSheet.CloseButtonColor
import com.example.adaptivestreamingplayer.ui.theme.appFontFamily

@Composable
fun FanCard(
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

    val scale = remember(boxWidth) {
        with(density) { boxWidth.toPx() / 77f }
    }

    val statusText = remember(device.deviceStatus) {
        when (device.deviceStatus) {
            in listOf("1", "2", "3", "4", "5") -> "Speed ${device.deviceStatus}"
            else -> "Off"
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
                onSelectedDevice(DeviceActions.FanSpeed(device, device.deviceStatus))
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

@Composable
fun FanIcon(
    modifier: Modifier = Modifier,
    isOn: Boolean,
    speed: String,
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
        painter = painterResource(R.drawable.ic_fan),
        contentDescription = if (isOn) "Fan running at speed $speed" else "Fan off",
        modifier = modifier.rotate(rotation),
        tint = iconTint
    )
}

@Composable
fun FanSpeedModelBottomSheet(
    modifier: Modifier = Modifier,
    modalBottomSheetState: SheetState = rememberModalBottomSheetState(),
    sheetMaxWidth: Dp = BottomSheetDefaults.SheetMaxWidth,
    shape: Shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    onDismiss: () -> Unit = {},
    onAction: (DeviceActions) -> Unit = {},
    selectedDevice: Device?
) {
    ModalBottomSheet(
        sheetState = modalBottomSheetState,
        sheetMaxWidth = sheetMaxWidth,
        modifier = modifier,
        onDismissRequest = onDismiss,
        containerColor = BottomSheetBackgroundColor,
        shape = shape,
        dragHandle = null
    ) {
        FanSpeedSheetContent(
            onDismiss = onDismiss,
            onAction = onAction,
            selectedDevice = selectedDevice
        )
    }
}

@Composable
fun FanSpeedSheetContent(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onAction: (DeviceActions) -> Unit,
    selectedDevice: Device?
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(BottomSheetBackgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.padding(bottom = 24.dp)
        ) {
            Text(
                text = "Set Fan Speed",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1B1A40),
                modifier = Modifier.fillMaxWidth()
                    .align(Alignment.Center),
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .size(32.dp)
                    .clip(CircleShape)
                    .background(Color.White.copy(alpha = 0.8f))
                    .align(Alignment.CenterEnd)
                    .clickable { onDismiss() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Outlined.Close,
                    contentDescription = "Close",
                    tint = CloseButtonColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        LazyRow(
            modifier = Modifier
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            items (5) {
                FanSpeedSwitch(
                    fanSpeedNumber = "$it",
                    selectedDevice = selectedDevice,
                    onAction = onAction
                )
            }
        }
    }
}


@Composable
fun FanSpeedSwitch(
    modifier: Modifier = Modifier,
    fanSpeedNumber: String,
    onAction: (DeviceActions) -> Unit,
    selectedDevice: Device?
) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .border(2.dp, CloseButtonColor, CircleShape)
            .background(Color.White, CircleShape)
            .size(50.dp)
            .clickable {
                selectedDevice?.let {
                    onAction(DeviceActions.FanSpeed(it, fanSpeedNumber))
                }
            }
    ) {
        Text(
            text = if(fanSpeedNumber == "0") "Off" else fanSpeedNumber,
            color = Color(0xFF35353A),
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            fontFamily = appFontFamily,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}


@Preview
@Composable
fun FanCardPreview() {
    FanCard(
        device = Device(
            type = "fan",
            name = "Fan",
            deviceStatus = "5"
        ),
        cornerRadius = 16.dp,
        strokeWidth = 5.dp,
        boxWidth = 105.dp,
        onSelectedDevice = {},
    )
}

@Preview
@Composable
fun FanIconPreview() {
    FanIcon(
        isOn= true,
        speed= "0",
        modifier = Modifier
    )
}


@Preview
@Composable
fun FanSpeedSheetContentPreview() {
    FanSpeedSheetContent(
        onDismiss = {},
        onAction= {},
        selectedDevice = Device(
            type = "fan",
            name = "Fan",
            deviceStatus = "5"
        )
    )
}
@Preview
@Composable
fun FanSpeedSwitchPreview() {
    FanSpeedSwitch(
        modifier = Modifier,
        fanSpeedNumber = "0",
        selectedDevice = Device(
            type = "Fan",
            name = "Fan",
            deviceStatus = "4",
        ),
        onAction = {},
    )
}