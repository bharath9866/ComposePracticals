package com.example.adaptivestreamingplayer.gSmart.otp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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

    OTPScreen(
        phoneNumber = phoneNumber,
        onOTPVerified = { otp ->
            // Handle OTP verification
            println("OTP Verified: $otp")
        },
        onResendOTP = {
            // Handle resend OTP
            println("Resend OTP")
        }
    )
}

@Composable
fun OTPScreen(
    modifier: Modifier = Modifier,
    phoneNumber: String = "",
    onOTPVerified: (String) -> Unit = {},
    onResendOTP: () -> Unit = {},
) {
    var otpValue by remember { mutableStateOf("") }
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
                OTPInputField(
                    otpValue = otpValue,
                    onOTPChange = { otp ->
                        if (otp.length <= 4) {
                            otpValue = otp
                        }
                    }
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
                                onResendOTP()
                                timeLeft = ResendOTPTimerLimit
                                isTimerRunning = true
                                otpValue = ""
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
                        if (otpValue.length == 4) {
                            onOTPVerified(otpValue)
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
                    enabled = otpValue.length == 4,
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

@Composable
fun OTPInputField(
    otpValue: String,
    onOTPChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val focusRequesters = remember { List(4) { FocusRequester() } }

    // Auto-focus next field effect
    LaunchedEffect(otpValue) {
        if (otpValue.isNotEmpty()) {
            val nextIndex = otpValue.length
            if (nextIndex < 4) {
                focusRequesters[nextIndex].requestFocus()
            }
        }
    }

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        repeat(4) { index ->
            val char = if (index < otpValue.length) otpValue[index].toString() else ""
            val isFocused = index == otpValue.length

            BasicTextField(
                value = char,
                onValueChange = { newValue ->
                    if (newValue.length <= 1 && newValue.all { it.isDigit() }) {
                        val newOTP = buildString {
                            repeat(4) { i ->
                                when {
                                    i < index -> append(otpValue.getOrNull(i) ?: "")
                                    i == index -> append(newValue)
                                    i < otpValue.length -> append(otpValue[i])
                                }
                            }
                        }
                        onOTPChange(newOTP)
                    }
                },
                modifier = Modifier
                    .size(70.dp)
                    .focusRequester(focusRequesters[index])
                    .clip(RoundedCornerShape(12.dp))
                    .background(OTPFieldColor)
                    .border(
                        width = if (isFocused) 2.dp else 1.dp,
                        color = if (isFocused) OTPTextColor else Color.Transparent,
                        shape = RoundedCornerShape(12.dp)
                    ),
                textStyle = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = OTPTextColor,
                    textAlign = TextAlign.Center
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        innerTextField()
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OTPScreenPreview(modifier: Modifier = Modifier) {
    OTPScreen()
}
