@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.adaptivestreamingplayer.gSmart

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.gSmart.onBoarding.onBoardingBottomSheet.BottomSheetBackgroundColor
import com.example.adaptivestreamingplayer.gSmart.onBoarding.onBoardingBottomSheet.CloseButtonColor

@Composable
fun TimerBottomSheet(
    modifier: Modifier = Modifier,
    modalBottomSheetState: SheetState = rememberModalBottomSheetState(),
    sheetMaxWidth: Dp = BottomSheetDefaults.SheetMaxWidth,
    shape: Shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
    onDeviceUiActions: (DeviceUiActions) -> Unit = {},
    selectedDevice: Device
) {
    ModalBottomSheet(
        sheetState = modalBottomSheetState,
        sheetMaxWidth = sheetMaxWidth,
        modifier = modifier,
        onDismissRequest = { onDeviceUiActions(DeviceUiActions.Dismiss) },
        containerColor = BottomSheetBackgroundColor,
        shape = shape,
        dragHandle = null
    ) {
        TimerSheetContent(
            onDeviceUiActions = onDeviceUiActions,
            selectedDevice = selectedDevice
        )
    }
}

@Composable
fun TimerSheetContent(
    modifier: Modifier = Modifier,
    onDeviceUiActions: (DeviceUiActions) -> Unit = {},
    selectedDevice: Device
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .background(BottomSheetBackgroundColor)
            .padding(24.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            ""
        )
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(Color.White.copy(alpha = 0.8f))
                .align(Alignment.End)
                .clickable { onDeviceUiActions(DeviceUiActions.Dismiss) },
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Outlined.Close,
                contentDescription = "Close",
                tint = CloseButtonColor,
                modifier = Modifier.size(20.dp)
            )
        }

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "TIMER",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold
            )

            Button(
                onClick = {

                },
                modifier = Modifier
                    .wrapContentSize(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1B1A40),
                    contentColor = Color(0xFFFFDE69)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "SCHEDULE",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Button(
                onClick = {

                },
                modifier = Modifier
                    .wrapContentSize(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1B1A40),
                    contentColor = Color(0xFFFFDE69)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "SCHEDULES LIST",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Button(
                onClick = {

                },
                modifier = Modifier
                    .wrapContentSize(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1B1A40),
                    contentColor = Color(0xFFFFDE69)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "RENAME",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Button(
                onClick = {

                },
                modifier = Modifier
                    .wrapContentSize(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF1B1A40),
                    contentColor = Color(0xFFFFDE69)
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    text = "NOTIFICATION STATUS",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Preview
@Composable
fun TimerSheetContentPreview() {
    TimerSheetContent(
       selectedDevice = Device()
    )
}

sealed interface DeviceUiActions {
    data object Dismiss: DeviceUiActions
    data class OpenFanSettingBottomSheet(val device: Device): DeviceUiActions
    data class EditDevice(val device: Device): DeviceUiActions
    data class SetFanSpeed(val device: Device, val deviceFanSpeed: String): DeviceUiActions
    data class OpenTimerDialog(val device: Device): DeviceUiActions
    data class ConfirmTimer(val device: Device, val timePickerState: TimePickerState, val isActionOn: Boolean): DeviceUiActions
    data class DatePicker(val device: Device, val timePickerState: TimePickerState): DeviceUiActions
    data class LampOnOff(val device: Device, val deviceStatus: String): DeviceUiActions
}