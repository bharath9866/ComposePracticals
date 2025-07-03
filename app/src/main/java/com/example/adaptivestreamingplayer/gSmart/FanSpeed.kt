package com.example.adaptivestreamingplayer.gSmart

import android.R.attr.contentDescription
import android.R.attr.rotation
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.adaptivestreamingplayer.R

@Preview
@Composable
fun FanIcon(
    isOn: Boolean = true,
    speed: String = "5",
    modifier: Modifier = Modifier
) {
    val transition = rememberInfiniteTransition("fan_transition")

    val rotation by transition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = when (speed) {
                    "1" -> 3000
                    "2" -> 2000
                    "3" -> 1500
                    "4" -> 1000
                    "5" -> 500
                    else -> 0
                },
                easing = LinearEasing
            )
        ),
        label = "fan_rotation"
    )

    Icon(
        painter = painterResource(R.drawable.ic_fan),
        contentDescription = "Fan $speed",
        modifier = modifier.rotate(rotation),
        tint = if (isOn) Color.White else Color(0xFF9E9E9E)
    )
}

@Preview
@Composable
private fun CardBackground() {

}

@Preview
@Composable
private fun FanCard(device: Device = Device(), onSelectedCard: (DeviceActions) -> Unit = {}) {
    var isOn = remember(device.isDeviceOn) { derivedStateOf { device.isDeviceOn }.value }

    val backgroundColors = animateColorList(
        onColors = listOf(Color(0xFF39C7FF), Color(0xFF39C7FF), Color(0xFF39C7FF)),
        offColors = listOf(Color(0xFF4A4A4A), Color(0xFF121212)),
        isOn = isOn
    )

    val iconColors = animateColorList(
        onColors = listOf(Color(0xFF82DAFD)),
        offColors = listOf(Color(0xFF6C6C6C), Color(0xFF333333)),
        isOn = isOn
    )

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .width(105.dp)
            .aspectRatio(0.7664f)
            .background(brush = Brush.linearGradient(colors = backgroundColors))
            .clickable { onSelectedCard(DeviceActions.OnPublish(device, !device.isDeviceOn)) }
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .paint(painter = painterResource(R.drawable.device_card_bg))
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
                .paint(painter = painterResource(R.drawable.device_type_background)),
            contentAlignment = Alignment.Center
        ) {
            FanIcon(
                isOn = isOn,
                speed = device.deviceStatus,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp)
            )
        }
        device.name?.let {
            Text(
                text = it,
                modifier = Modifier.basicMarquee(),
                color = Color.White,
                fontSize = 18.sp,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

sealed interface DeviceActions {
    data class OnPublish(val device: Device, val isDeviceOn: Boolean): DeviceActions
    data class FanSpeed(val device: Device, val speedNumber: Int): DeviceActions
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
    val isDeviceOn: Boolean = true,
    val deviceStatus: String = "4"
)