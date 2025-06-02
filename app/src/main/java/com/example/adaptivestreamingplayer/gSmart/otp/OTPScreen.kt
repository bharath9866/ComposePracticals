package com.example.adaptivestreamingplayer.gSmart.otp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.adaptivestreamingplayer.R
import com.example.adaptivestreamingplayer.gSmart.onBoarding.TransparentStatusBar
import com.example.adaptivestreamingplayer.gSmart.onBoarding.onBoardingBottomSheet.BottomSheetType
import kotlinx.coroutines.delay
import java.util.Locale

val OTPBackgroundColor = Color(0xFFFFFFFF) // Light gray background
val OTPFieldColor = Color(0xFFE8E8E8) // Light gray for OTP fields
val OTPTextColor = Color(0xFF1B1A40) // Dark blue text
val OTPButtonColor = Color(0xFF1B1A40) // Dark blue button
val OTPButtonTextColor = Color(0xFFFFDE69) // Yellow button text
const val ResendOTPTimerLimit = 5 // In Seconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OTPScreenRoute(
    navController: NavHostController,
    isFor: BottomSheetType,
    phoneNumber: String
) {
    TransparentStatusBar(appBackgroundColor = OTPBackgroundColor)
    val viewModel = viewModel<OtpViewModel>()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val focusRequesters = remember { List(4) { FocusRequester() } }

    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.focusedIndex) {
        state.focusedIndex?.let { index ->
            focusRequesters.getOrNull(index)?.requestFocus()
        }
    }

    LaunchedEffect(state.code, keyboardManager) {
        val allNumbersEntered = state.code.none { it == null }
        if(allNumbersEntered) {
            focusRequesters.forEach {
                it.freeFocus()
            }
            focusManager.clearFocus()
            keyboardManager?.hide()
        }
    }

    OTPScreen(
        phoneNumber = phoneNumber,
        state = state,
        focusRequesters = focusRequesters,
        onAction = { action ->
            when(action) {
                is OtpAction.OnEnterNumber -> {
                    if(action.number != null) {
                        focusRequesters[action.index].freeFocus()
                    }
                }
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun OTPScreen(
    modifier: Modifier = Modifier,
    phoneNumber: String = "",
    state: OtpState,
    focusRequesters: List<FocusRequester>,
    onAction: (OtpAction) -> Unit = {} ,
) {
    var timeLeft by remember { mutableIntStateOf(ResendOTPTimerLimit) } // 2 minutes in seconds
    var isTimerRunning by remember { mutableStateOf(true) }

    // Timer countdown effect
    LaunchedEffect(isTimerRunning, timeLeft) {
        if (isTimerRunning && timeLeft > 0) {
            delay(1000)
            timeLeft--
        } else if (timeLeft == 0) {
            isTimerRunning = false
        }
    }

    // Format timer display
    val formattedTime = String.format(Locale.getDefault(), "%02d:%02d Sec", timeLeft / 60, timeLeft % 60)

    Scaffold(
        containerColor = OTPBackgroundColor,
        contentWindowInsets = WindowInsets(0, 0, 0, 0)
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.statusBars)
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top section with illustration
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.weight(1f)
            ) {
                Spacer(modifier = Modifier.height(40.dp))

                // Illustration
                Image(
                    painter = painterResource(R.drawable.otp_screen_img), // Replace with OTP illustration
                    contentDescription = "OTP Verification Illustration",
                    modifier = Modifier
                        .size(300.dp)
                        .padding(vertical = 32.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Title
                Text(
                    text = "OTP VERIFICATION",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = OTPTextColor,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Subtitle with phone number
                Text(
                    text = "Enter the OTP sent to - $phoneNumber",
                    fontSize = 16.sp,
                    color = Color(0xFF666666),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(48.dp))

                // OTP Input Fields
                OtpScreenPL(
                    state = state,
                    focusRequesters = focusRequesters,
                    onAction = { action ->
                        when(action) {
                            is OtpAction.OnEnterNumber -> {
                                if(action.number != null) {
                                    focusRequesters[action.index].freeFocus()
                                }
                            }
                            else -> Unit
                        }
                        onAction(action)
                    },
                    modifier = Modifier
                        .padding(innerPadding)
                        .consumeWindowInsets(innerPadding)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // Timer
                Text(
                    text = formattedTime,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = if (timeLeft > 0) OTPTextColor else Color(0xFFEF5858),
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Resend text
                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Don't receive code ? ",
                        fontSize = 16.sp,
                        color = Color(0xFF666666)
                    )
                    Text(
                        text = "Re-send",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = if (timeLeft == 0) Color(0xFFEF5858) else Color(0xFF999999),
                        modifier = Modifier.clickable(enabled = timeLeft == 0) {
                            if (timeLeft == 0) {
                                onAction(OtpAction.ResetOTPField)
                                timeLeft = ResendOTPTimerLimit
                                isTimerRunning = true
                            }
                        }
                    )
                }
            }

            // Bottom section with submit button
            Column {
                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = {
                        if (state.isValid == true) {
                            Log.d("onOtpSubmit", "${state}")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = OTPButtonColor,
                        contentColor = Color.White,
                        disabledContainerColor = Color(0xFFCCCCCC),
                        disabledContentColor = Color(0xFF666666)
                    ),
                    enabled = state.isValid == true,
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Submit",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OTPScreenPreview(modifier: Modifier = Modifier) {
    OTPScreen(
        modifier = Modifier,
        phoneNumber = "1234567890",
        state = OtpState(
            listOf(1, 2, 3, 4),
            focusedIndex = 3,
            isValid = true
        ),
        focusRequesters = listOf(
            FocusRequester(),
            FocusRequester(),
            FocusRequester(),
            FocusRequester()),
        onAction = {  }
    )
}
