package com.example.adaptivestreamingplayer

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.utils.PreviewAll
import com.example.adaptivestreamingplayer.utils.PreviewNexusTen

@Preview
@Composable
fun SampleScreen() {
    Box(
        modifier = Modifier
        .fillMaxSize()
        .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Bharath",
            fontSize = 35.sp,
            fontStyle = FontStyle.Italic,
            fontWeight = FontWeight.Bold,
            color = Color.Yellow
        )
    }
}
