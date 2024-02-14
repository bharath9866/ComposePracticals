package com.example.adaptivestreamingplayer.jetlagged

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.adaptivestreamingplayer.ui.theme.SmallHeadingStyle
import com.example.adaptivestreamingplayer.ui.theme.Yellow
import com.example.adaptivestreamingplayer.ui.theme.YellowVariant

@Preview
@Composable
fun JetLagged() {
    val sleepState by remember { mutableStateOf(sleepData) }
    val hours = (sleepState.earliestStartHour..23) + (0..sleepState.latestEndHour)
    HoursHeader(hours)
}

@Composable
fun TimeGraph(
    hoursHeader: @Composable () -> Unit,
    dayItemsCount:Int,
    dayLabel: @Composable (index: Int) -> Unit,
    sleepBar: @Composable (index: Int) -> Unit,
) {
    val dayLabels = @Composable { repeat(dayItemsCount) { index -> dayLabel(index) } }
    val sleepBars = @Composable { repeat(dayItemsCount) { index -> sleepBar(index) } }

}

@Composable
fun HoursHeader(hours: List<Int>) {
    Row(
        Modifier
            .padding(bottom = 16.dp)
            .drawBehind {
                val brush = Brush.linearGradient(listOf(YellowVariant, Yellow))
                drawRoundRect(
                    brush = brush,
                    cornerRadius = CornerRadius(x = 10.dp.toPx(), y = 10.dp.toPx())
                )
            }
    ) {
        hours.forEach {
            Text(
                text = it.toString(),
                modifier = Modifier
                    .width(50.dp)
                    .padding(vertical = 4.dp),
                textAlign = TextAlign.Center,
                style = SmallHeadingStyle
            )
        }
    }
}