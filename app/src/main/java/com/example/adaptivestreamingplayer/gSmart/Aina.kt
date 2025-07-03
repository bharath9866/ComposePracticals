package com.example.adaptivestreamingplayer.gSmart

import android.R.attr.text
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.adaptivestreamingplayer.R

@Preview
@Composable
private fun AinaPreview() {
    var isOn by remember { mutableStateOf(true) }

    // Colors for card background and icon background
    val backgroundColors = animateColorList(
        onColors = listOf(Color(0xFF39C7FF), Color(0xFF39C7FF), Color(0xFF39C7FF)),
        offColors = listOf(Color(0xFF4A4A4A), Color(0xFF121212)),
        isOn = isOn
    )

    val iconColors = animateColorList(
        onColors = listOf(Color(0xFF82DAFD)),
        offColors = listOf(Color(0xFF6C6C6C), Color(0xFF333333)),
        isOn = isOn
    )

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(25.dp))
            .width(105.dp)
            .aspectRatio(0.7664f)
            .background(brush = Brush.linearGradient(colors = backgroundColors))
            .clickable { isOn = !isOn } // Toggle state
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceAround
    ) {
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .size(50.dp)
                .background(brush = Brush.verticalGradient(colors = iconColors.map { it.copy(alpha = 0.6f) }))
                .then(
                    if (isOn)
                        Modifier.border(
                            width = 1.dp,
                            brush = Brush.linearGradient(iconColors),
                            shape = CircleShape
                        )
                    else
                        Modifier
                ),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.device_lamp_logo),
                contentDescription = "Device Logo",
                modifier = Modifier.fillMaxSize()
            )
        }

        Text(
            text = if (isOn) "Light ON" else "Light OFF",
            modifier = Modifier.basicMarquee(),
            color = Color.White,
            fontSize = 18.sp,
            fontWeight = FontWeight.Normal,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun animateColorList(onColors: List<Color>, offColors: List<Color>, isOn: Boolean): List<Color> {
    val transition = updateTransition(targetState = isOn, label = "PowerToggle")
    val maxSize = maxOf(onColors.size, offColors.size)
    return List(maxSize) { index ->
        transition.animateColor(label = "Color$index") { state ->
            val onColor = onColors.getOrElse(index) { onColors.last() }
            val offColor = offColors.getOrElse(index) { offColors.last() }
            if (state) onColor else offColor
        }.value
    }
}