package com.example.adaptivestreamingplayer.composeBasics

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun ColumnAlignExample() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color(0xFF86efac), RoundedCornerShape(4.dp))
            .background(Color(0xFFbbf7d0), RoundedCornerShape(4.dp))
            .padding(16.dp)
    ) {
        // Align to start (left)
        Text(
            text = "Start",
            modifier = Modifier
                .background(Color(0xFF60a5fa), RoundedCornerShape(4.dp))
                .align(Alignment.Start)
                .padding(4.dp),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Align to center horizontally
        Text(
            text = "CenterHorizontally",
            modifier = Modifier
                .background(Color(0xFFf87171), RoundedCornerShape(4.dp))
                .align(Alignment.CenterHorizontally)
                .padding(4.dp),
            color = Color.White
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Align to end (right)
        Text(
            text = "End",
            modifier = Modifier
                .background(Color(0xFFc084fc), RoundedCornerShape(4.dp))
                .align(Alignment.End)
                .padding(4.dp),
            color = Color.White
        )
    }
}

// Available alignments in Column:
// Alignment.Start, Alignment.CenterHorizontally, Alignment.End