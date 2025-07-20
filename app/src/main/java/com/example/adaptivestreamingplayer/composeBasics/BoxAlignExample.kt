package com.example.adaptivestreamingplayer.composeBasics

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun BoxAlignExample() {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .border(2.dp, Color(0xFF93c5fd), RoundedCornerShape(4.dp))
            .background(Color(0xFFbfdbfe), RoundedCornerShape(4.dp))
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)
    ) {
        // Top Start
        Text(
            text = "TopStart",
            modifier = Modifier
                .background(Color(0xFFf87171), RoundedCornerShape(4.dp))
                .align(Alignment.TopStart)
                .padding(4.dp),
            color = Color.White,
        )
        
        // Top End
        Text(
            text = "TopEnd",
            modifier = Modifier
                .background(Color(0xFF4ade80), RoundedCornerShape(4.dp))
                .align(Alignment.TopEnd).padding(4.dp),
            color = Color.White,
        )
        
        // Center
        Text(
            text = "Center",
            modifier = Modifier
                .background(Color(0xFF2563eb), RoundedCornerShape(4.dp))
                .align(Alignment.Center).padding(4.dp),
            color = Color.White,
        )
        
        // Bottom Start
        Text(
            text = "BottomStart",
            modifier = Modifier
                .background(Color(0xFFc084fc), RoundedCornerShape(4.dp))
                .align(Alignment.BottomStart).padding(4.dp),
            color = Color.White,
        )
        
        // Bottom End
        Text(
            text = "BottomEnd",
            modifier = Modifier
                .background(Color(0xFFeab308), RoundedCornerShape(4.dp))
                .align(Alignment.BottomEnd).padding(4.dp),
            color = Color.White,
        )
    }
}

// Available alignments in Box:
// Alignment.TopStart, Alignment.TopCenter, Alignment.TopEnd
// Alignment.CenterStart, Alignment.Center, Alignment.CenterEnd  
// Alignment.BottomStart, Alignment.BottomCenter, Alignment.BottomEnd