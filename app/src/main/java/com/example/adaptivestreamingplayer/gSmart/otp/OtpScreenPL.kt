package com.example.adaptivestreamingplayer.gSmart.otp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpScreenPL(
    state: OtpState,
    focusRequesters: List<FocusRequester>,
    onAction: (OtpAction) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            state.code.forEachIndexed { index, number ->
                OtpInputField(
                    number = number,
                    focusRequester = focusRequesters[index],
                    onFocusChanged = { isFocused ->
                        if(isFocused) {
                            onAction(OtpAction.OnChangeFieldFocused(index))
                        }
                    },
                    onNumberChanged = { newNumber ->
                        onAction(OtpAction.OnEnterNumber(newNumber, index))
                    },
                    onKeyboardBack = {
                        onAction(OtpAction.OnKeyboardBack)
                    },
                    modifier = Modifier
                        .size(70.dp)
                        .aspectRatio(1f)
                )
            }
        }

        state.isValid?.let { isValid ->
            Text(
                text = if(isValid) "OTP is valid!" else "OTP is invalid!",
                color = if(isValid) PLCodingGreen else Color.Red,
                fontSize = 16.sp
            )
        }
    }
}

val PLCodingGreen = Color(0xff00f15e)
val PLCodingLightGray = Color(0xff262626)

@Preview
@Composable
fun OtpScreenPLPreview() {
    OtpScreenPL(
        state = OtpState(
            code = listOf(1, 2, 3, 4),
            focusedIndex = 3,
            isValid = true,
        ),
        focusRequesters = List(4) { FocusRequester() },
        onAction = {}
    )
}
