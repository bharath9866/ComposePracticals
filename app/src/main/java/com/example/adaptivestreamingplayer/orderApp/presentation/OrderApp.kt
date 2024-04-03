package com.example.adaptivestreamingplayer.orderApp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF,
    device = "spec:parent=pixel_5,orientation=landscape"
)
@Composable
fun OrderAppScreen() {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Header(Modifier.padding(horizontal = 16.dp))
        }
        item {
            Body(Modifier.padding(horizontal = 16.dp))
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun BottomSheet() {
    Column(modifier = Modifier
        .fillMaxWidth(0.5f)
        .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "Schedule cooking time",
            color = Color(0xFF32328c),
            fontSize = 11.sp,
            modifier = Modifier.padding(start = 2.dp, end = 8.dp),
            fontWeight = FontWeight.Bold
        )
    }
}