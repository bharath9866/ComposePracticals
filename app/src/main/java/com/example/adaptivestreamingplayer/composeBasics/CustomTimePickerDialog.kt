package com.example.adaptivestreamingplayer.composeBasics

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.EditCalendar
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimeInput
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDefaults
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerDialog(
    onDismiss: () -> Unit,
    onConfirm: (TimePickerState) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentTime = Calendar.getInstance()
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = false,
    )

    var showDial by remember { mutableStateOf(true) }

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = true,
            dismissOnBackPress = true
        )
    ) {
        Surface(
            modifier = modifier
                .fillMaxWidth(0.92f)
                .fillMaxHeight(0.85f) // Limit height to prevent overflow
                .clip(RoundedCornerShape(28.dp)),
            shape = RoundedCornerShape(28.dp),
            color = Color(0xFFF4E9A3),
            tonalElevation = 0.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()) // Enable scrolling
                    .padding(24.dp)
            ) {
                // Header Section
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Set Time",
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF2C2C2C)
                        )
                    )

                    Surface(
                        onClick = onDismiss,
                        shape = CircleShape,
                        color = Color.White,
                        modifier = Modifier.size(32.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = "Close",
                                tint = Color(0xFFE53935),
                                modifier = Modifier.size(18.dp)
                            )
                        }
                    }
                }

                // Time Display Card
                Surface(
                    shape = RoundedCornerShape(16.dp),
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Hour Display
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFE53935),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Text(
                                text = String.format("%02d",
                                    if (timePickerState.is24hour) timePickerState.hour
                                    else if (timePickerState.hour == 0) 12
                                    else if (timePickerState.hour > 12) timePickerState.hour - 12
                                    else timePickerState.hour
                                ),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                ),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }

                        Text(
                            text = ":",
                            style = MaterialTheme.typography.headlineMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF2C2C2C)
                            ),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )

                        // Minute Display
                        Surface(
                            shape = RoundedCornerShape(12.dp),
                            color = Color(0xFFE53935),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                text = String.format("%02d", timePickerState.minute),
                                style = MaterialTheme.typography.headlineMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                ),
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }

                        if (!timePickerState.is24hour) {
                            Spacer(modifier = Modifier.width(12.dp))
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color(0xFFFFC107),
                                modifier = Modifier.padding(start = 8.dp)
                            ) {
                                Text(
                                    text = if (timePickerState.hour < 12) "AM" else "PM",
                                    style = MaterialTheme.typography.labelLarge.copy(
                                        fontWeight = FontWeight.Bold,
                                        color = Color(0xFF2C2C2C)
                                    ),
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                                )
                            }
                        }
                    }
                }

                // Mode Toggle
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Surface(
                        shape = RoundedCornerShape(25.dp),
                        color = Color.White,
                        border = BorderStroke(2.dp, Color(0xFFE53935))
                    ) {
                        Row(
                            modifier = Modifier.padding(4.dp)
                        ) {
                            Surface(
                                onClick = { showDial = true },
                                shape = RoundedCornerShape(20.dp),
                                color = if (showDial) Color(0xFFE53935) else Color.Transparent,
                                modifier = Modifier.padding(2.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.AccessTime,
                                        contentDescription = "Clock",
                                        tint = if (showDial) Color.White else Color(0xFFE53935),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Clock",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Medium,
                                            color = if (showDial) Color.White else Color(0xFFE53935)
                                        )
                                    )
                                }
                            }

                            Surface(
                                onClick = { showDial = false },
                                shape = RoundedCornerShape(20.dp),
                                color = if (!showDial) Color(0xFFE53935) else Color.Transparent,
                                modifier = Modifier.padding(2.dp)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.EditCalendar,
                                        contentDescription = "Input",
                                        tint = if (!showDial) Color.White else Color(0xFFE53935),
                                        modifier = Modifier.size(16.dp)
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "Input",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            fontWeight = FontWeight.Medium,
                                            color = if (!showDial) Color.White else Color(0xFFE53935)
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                // TimePicker Content
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.White,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 20.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        if (showDial) {
                            TimePicker(
                                state = timePickerState,
                                colors = TimePickerDefaults.colors(
                                    clockDialColor = Color(0xFFFFFBF0),
                                    selectorColor = Color(0xFFE53935),
                                    containerColor = Color.Transparent,
                                    periodSelectorBorderColor = Color(0xFFE53935),
                                    clockDialSelectedContentColor = Color.White,
                                    clockDialUnselectedContentColor = Color(0xFF424242),
                                    periodSelectorSelectedContainerColor = Color(0xFFE53935),
                                    periodSelectorUnselectedContainerColor = Color(0xFFFFF8E1),
                                    periodSelectorSelectedContentColor = Color.White,
                                    periodSelectorUnselectedContentColor = Color(0xFF424242),
                                    timeSelectorSelectedContainerColor = Color(0xFFFFC107),
                                    timeSelectorUnselectedContainerColor = Color.Transparent,
                                    timeSelectorSelectedContentColor = Color(0xFF212121),
                                    timeSelectorUnselectedContentColor = Color(0xFF616161)
                                )
                            )
                        } else {
                            TimeInput(
                                state = timePickerState,
                                colors = TimePickerDefaults.colors(
                                    clockDialColor = Color(0xFFFFFBF0),
                                    selectorColor = Color(0xFFE53935),
                                    containerColor = Color.Transparent,
                                    periodSelectorBorderColor = Color(0xFFE53935),
                                    clockDialSelectedContentColor = Color.White,
                                    clockDialUnselectedContentColor = Color(0xFF424242),
                                    periodSelectorSelectedContainerColor = Color(0xFFE53935),
                                    periodSelectorUnselectedContainerColor = Color(0xFFFFF8E1),
                                    periodSelectorSelectedContentColor = Color.White,
                                    periodSelectorUnselectedContentColor = Color(0xFF424242),
                                    timeSelectorSelectedContainerColor = Color(0xFFFFC107),
                                    timeSelectorUnselectedContainerColor = Color.Transparent,
                                    timeSelectorSelectedContentColor = Color(0xFF212121),
                                    timeSelectorUnselectedContentColor = Color(0xFF616161)
                                )
                            )
                        }
                    }
                }

                // Action Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        onClick = onDismiss,
                        shape = RoundedCornerShape(16.dp),
                        color = Color.White,
                        border = BorderStroke(2.dp, Color(0xFFE0E0E0)),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "Cancel",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color(0xFF757575)
                                )
                            )
                        }
                    }

                    Surface(
                        onClick = { onConfirm(timePickerState) },
                        shape = RoundedCornerShape(16.dp),
                        color = Color(0xFFE53935),
                        modifier = Modifier
                            .weight(1f)
                            .height(48.dp)
                    ) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Text(
                                text = "Set Time",
                                style = MaterialTheme.typography.titleMedium.copy(
                                    fontWeight = FontWeight.SemiBold,
                                    color = Color.White
                                )
                            )
                        }
                    }
                }
            }
        }
    }
}

// Usage Example
@SuppressLint("DefaultLocale")
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTimePickerPreview() {
    MaterialTheme {
        var showDialog by remember { mutableStateOf(true) }
        var selectedTime by remember { mutableStateOf<String?>(null) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE53935)
                )
            ) {
                Text("Open Custom Time Picker")
            }

            selectedTime?.let { time ->
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Selected Time: $time",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        if (showDialog) {
            CustomTimePickerDialog(
                onDismiss = { showDialog = false },
                onConfirm = { timePickerState ->
                    val hour = if (timePickerState.is24hour) {
                        timePickerState.hour
                    } else {
                        if (timePickerState.hour == 0) 12
                        else if (timePickerState.hour > 12) timePickerState.hour - 12
                        else timePickerState.hour
                    }
                    val minute = timePickerState.minute
                    val amPm = if (!timePickerState.is24hour) {
                        if (timePickerState.hour < 12) "AM" else "PM"
                    } else ""

                    selectedTime = String.format("%02d:%02d %s", hour, minute, amPm).trim()
                    showDialog = false
                }
            )
        }
    }
}