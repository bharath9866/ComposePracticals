package com.example.adaptivestreamingplayer.gSmart.devices

import android.R.attr.iconTint
import android.R.attr.rotation
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
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

@Composable
fun LampCardGlassMorphism(
    device: Device,
    cornerRadius: Dp = 24.dp,
    strokeWidth: Dp = 1.dp,
    boxWidth: Dp = 120.dp,
    onSelectedDevice: (DeviceActions) -> Unit,
) {
    val isOn by remember(device.deviceStatus) {
        derivedStateOf { device.isDeviceOn }
    }

    val statusText by remember(device.deviceStatus) {
        derivedStateOf {
            if (device.deviceStatus == "1") "On" else "Off"
        }
    }

    val animatedScale by animateFloatAsState(
        targetValue = if (isOn) 1.05f else 1f,
        animationSpec = spring(dampingRatio = 0.7f),
        label = "glass_scale"
    )

    Card(
        modifier = Modifier
            .width(boxWidth)
            .aspectRatio(0.8f)
            .scale(animatedScale)
            .clickable {
                onSelectedDevice(
                    DeviceActions.OnPublish(device, if (isOn) "0" else "1")
                )
            },
        shape = RoundedCornerShape(cornerRadius),
        colors = CardDefaults.cardColors(
            containerColor = if (isOn) {
                Color(0xFF29B6F6).copy(alpha = 0.15f)
            } else {
                Color(0xFF424242).copy(alpha = 0.15f)
            }
        ),
        border = BorderStroke(
            strokeWidth,
            if (isOn) Color.White.copy(alpha = 0.3f) else Color.Gray.copy(alpha = 0.2f)
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = if (isOn) 16.dp else 8.dp
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = if (isOn) {
                            listOf(
                                Color.White.copy(alpha = 0.1f),
                                Color(0xFF29B6F6).copy(alpha = 0.05f),
                                Color.Transparent
                            )
                        } else {
                            listOf(
                                Color.White.copy(alpha = 0.05f),
                                Color.Gray.copy(alpha = 0.02f),
                                Color.Transparent
                            )
                        },
                        start = Offset(0f, 0f),
                        end = Offset(Float.POSITIVE_INFINITY, Float.POSITIVE_INFINITY)
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                // Icon with backdrop blur effect
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .background(
                            color = if (isOn) Color.White.copy(alpha = 0.1f) else Color.Gray.copy(alpha = 0.05f),
                            shape = CircleShape
                        )
                        .border(
                            width = 1.dp,
                            color = if (isOn) Color.White.copy(alpha = 0.2f) else Color.Gray.copy(alpha = 0.1f),
                            shape = CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_bulb),
                        contentDescription = if (isOn) "Lamp On" else "Lamp off",
                        tint = if (isOn) Color.White else Color(0xFF9E9E9E),
                        modifier = Modifier.size(32.dp)
                    )
                }

                device.name?.let { name ->
                    Text(
                        text = name,
                        modifier = Modifier.basicMarquee(),
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center
                    )
                }

                // Status chip with glass effect
                Surface(
                    modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                    color = if (isOn) Color.White.copy(alpha = 0.15f) else Color.Gray.copy(alpha = 0.1f),
                    border = BorderStroke(
                        0.5.dp,
                        if (isOn) Color.White.copy(alpha = 0.3f) else Color.Gray.copy(alpha = 0.2f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(4.dp)
                                .background(
                                    color = if (isOn) Color(0xFF4CAF50) else Color(0xFFFF5722),
                                    shape = CircleShape
                                )
                        )
                        
                        Spacer(modifier = Modifier.width(6.dp))
                        
                        Text(
                            text = statusText,
                            color = Color.White,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun LampCardGlassMorphismPreview() {
    LampCardGlassMorphism(
        device = Device(
            type = "Light",
            name = "Light",
            deviceStatus = "0"
        ),
        cornerRadius = 16.dp,
        strokeWidth = 5.dp,
        boxWidth = 105.dp,
        onSelectedDevice = {},
    )
}