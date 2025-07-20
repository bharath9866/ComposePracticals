package com.example.adaptivestreamingplayer.ui.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.*

@Composable
fun TimePicker(
    selectedHour: Int = 0,
    selectedMinute: Int = 0,
    onTimeSelected: (hour: Int, minute: Int) -> Unit = { _, _ -> },
    onCancel: () -> Unit = {},
    onOk: () -> Unit = {}
) {
    var currentHour by remember { mutableStateOf(selectedHour) }
    var currentMinute by remember { mutableStateOf(selectedMinute) }
    var isSelectingMinutes by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Digital Display
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(
                    Color(0xFF2196F3),
                    RoundedCornerShape(8.dp)
                )
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = String.format("%02d", currentHour),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.clickable { isSelectingMinutes = false }
                )
                Text(
                    text = ":",
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Text(
                    text = String.format("%02d", currentMinute),
                    fontSize = 48.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    modifier = Modifier.clickable { isSelectingMinutes = true }
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Analog Clock Picker
        Box(
            modifier = Modifier.size(300.dp),
            contentAlignment = Alignment.Center
        ) {
            AnalogTimePicker(
                selectedValue = if (isSelectingMinutes) currentMinute else currentHour,
                isSelectingMinutes = isSelectingMinutes,
                onValueSelected = { value ->
                    if (isSelectingMinutes) {
                        currentMinute = value
                    } else {
                        currentHour = value
                    }
                    onTimeSelected(currentHour, currentMinute)
                }
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Calendar and Action Buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { /* Calendar action */ }
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowDown,
                    contentDescription = "Calendar",
                    tint = Color.Gray
                )
            }

            Row {
                TextButton(
                    onClick = onCancel
                ) {
                    Text(
                        text = "CANCEL",
                        color = Color(0xFF2196F3),
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))

                TextButton(
                    onClick = onOk
                ) {
                    Text(
                        text = "OK",
                        color = Color(0xFF2196F3),
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}

@Composable
private fun AnalogTimePicker(
    selectedValue: Int,
    isSelectingMinutes: Boolean,
    onValueSelected: (Int) -> Unit
) {
    val density = LocalDensity.current
    val radius = 120.dp
    val radiusPx = with(density) { radius.toPx() }

    Canvas(
        modifier = Modifier
            .size(radius * 2)
            .clickable { /* Handle touch for selection */ }
    ) {
        val center = size.center

        // Draw outer circle
        drawCircle(
            color = Color(0xFFE0E0E0),
            radius = radiusPx,
            center = center,
            style = Stroke(width = 2.dp.toPx())
        )

        // Draw numbers
        val maxValue = if (isSelectingMinutes) 60 else 12
        val step = if (isSelectingMinutes) 5 else 1

        for (i in 0 until maxValue step step) {
            val displayValue = if (isSelectingMinutes) {
                i
            } else {
                if (i == 0) 12 else i
            }

            val angle = (i * 360f / maxValue) - 90f
            val angleRad = Math.toRadians(angle.toDouble())

            val numberRadius = radiusPx - 40.dp.toPx()
            val x = center.x + (numberRadius * cos(angleRad)).toFloat()
            val y = center.y + (numberRadius * sin(angleRad)).toFloat()

            drawContext.canvas.nativeCanvas.drawText(
                displayValue.toString(),
                x,
                y + 16f,
                android.graphics.Paint().apply {
                    color = android.graphics.Color.BLACK
                    textSize = 32f
                    textAlign = android.graphics.Paint.Align.CENTER
                }
            )
        }

        // Draw inner circle for minutes (if selecting minutes)
        if (isSelectingMinutes) {
            for (i in 1..11) {
                val displayValue = i * 5
                val angle = (displayValue * 360f / 60) - 90f
                val angleRad = Math.toRadians(angle.toDouble())

                val innerRadius = radiusPx - 80.dp.toPx()
                val x = center.x + (innerRadius * cos(angleRad)).toFloat()
                val y = center.y + (innerRadius * sin(angleRad)).toFloat()

                drawContext.canvas.nativeCanvas.drawText(
                    displayValue.toString(),
                    x,
                    y + 12f,
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.GRAY
                        textSize = 24f
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }

        // Draw hand
        val handAngle = if (isSelectingMinutes) {
            (selectedValue * 360f / 60) - 90f
        } else {
            (selectedValue * 360f / 12) - 90f
        }

        val handAngleRad = Math.toRadians(handAngle.toDouble())
        val handLength = radiusPx - 60.dp.toPx()
        val handEndX = center.x + (handLength * cos(handAngleRad)).toFloat()
        val handEndY = center.y + (handLength * sin(handAngleRad)).toFloat()

        // Draw hand line
        drawLine(
            color = Color(0xFF2196F3),
            start = center,
            end = Offset(handEndX, handEndY),
            strokeWidth = 4.dp.toPx(),
            cap = StrokeCap.Round
        )

        // Draw center circle
        drawCircle(
            color = Color(0xFF2196F3),
            radius = 8.dp.toPx(),
            center = center
        )

        // Draw selection circle at hand end
        drawCircle(
            color = Color(0xFF2196F3),
            radius = 16.dp.toPx(),
            center = Offset(handEndX, handEndY)
        )

        drawCircle(
            color = Color.White,
            radius = 12.dp.toPx(),
            center = Offset(handEndX, handEndY)
        )

        // Draw selected value text inside selection circle
        drawContext.canvas.nativeCanvas.drawText(
            String.format("%02d", selectedValue),
            handEndX,
            handEndY + 8f,
            android.graphics.Paint().apply {
                color = android.graphics.Color.parseColor("#2196F3")
                textSize = 24f
                textAlign = android.graphics.Paint.Align.CENTER
                isFakeBoldText = true
            }
        )
    }
}

@Composable
fun TimePickerDialog(
    showDialog: Boolean,
    selectedHour: Int = 0,
    selectedMinute: Int = 0,
    onTimeSelected: (hour: Int, minute: Int) -> Unit,
    onDismiss: () -> Unit
) {
    if (showDialog) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            TimePicker(
                selectedHour = selectedHour,
                selectedMinute = selectedMinute,
                onTimeSelected = onTimeSelected,
                onCancel = onDismiss,
                onOk = {
                    onDismiss()
                }
            )
        }
    }
}