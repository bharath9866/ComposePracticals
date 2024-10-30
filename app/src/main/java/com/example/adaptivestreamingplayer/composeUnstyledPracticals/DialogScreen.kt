package com.example.adaptivestreamingplayer.composeUnstyledPracticals

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.displayCutoutPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.composables.core.Dialog
import com.composables.core.DialogPanel
import com.composables.core.rememberDialogState

@Preview
@Composable
fun DialogScreenPractical() {
    val dialogState = rememberDialogState(initiallyVisible = false)

    Box {
        Box(
            modifier = Modifier.fillMaxSize().clickable { dialogState.visible = true },
            contentAlignment = Alignment.Center
        ) {
            BasicText("Show Dialog")
        }
        Dialog(
            state = dialogState
        ) {
            DialogPanel(
                modifier = Modifier
                    .displayCutoutPadding()
                    .systemBarsPadding()
                    .widthIn(min = 280.dp, max = 560.dp)
                    .padding(20.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .border(1.dp, Color(0xFFE4E4E4), RoundedCornerShape(12.dp))
                    .background(Color.White),
            ) {
                Column {
                    Column(Modifier.padding(start = 24.dp, top = 24.dp, end = 24.dp)) {
                        BasicText(
                            text = "Update Available",
                            style = TextStyle(fontWeight = FontWeight.Medium)
                        )
                        Spacer(Modifier.height(8.dp))
                        BasicText(
                            text = "A new version of the app is available. Please update to the latest version.",
                            style = TextStyle(color = Color(0xFF474747))
                        )
                    }
                    Spacer(Modifier.height(24.dp))
                    Box(Modifier.padding(12.dp)
                        .align(Alignment.End)
                        .clip(RoundedCornerShape(4.dp))
                        .clickable(role = Role.Button) { dialogState.visible = false }
                        .padding(horizontal = 12.dp, vertical = 8.dp)) {
                        BasicText(
                            text = "Update",
                            style = TextStyle(color = Color(0xFF6699FF))
                        )
                    }
                }
            }
        }
    }
}