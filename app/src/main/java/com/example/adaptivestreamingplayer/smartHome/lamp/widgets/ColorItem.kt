package com.example.adaptivestreamingplayer.smartHome.lamp.widgets

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun ColorItem(color: Color, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(50.dp)
            .clip(CircleShape)
            .background(color, shape = CircleShape)
            .border(
                width = 5.dp,
                brush = if (isSelected) {
                    Brush.linearGradient(
                        colors = listOf(Color.Black, Color.Black)
                    )
                } else {
                    Brush.linearGradient(
                        colors = listOf(Color.Transparent,Color.Transparent)
                    )
                },
                shape = CircleShape
            )
            .clickable { onClick() }
    )
}


val COLOR_LIST = listOf(
    Color(0xFF5B42F9),
    Color(0xFFFB9700),
    Color(0xFF00B6D1),
    Color(0xFF4AB27A),
    Color(0xFFFF6161),
    Color(0xFFA97C50)
)