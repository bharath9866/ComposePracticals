package com.example.adaptivestreamingplayer.jetlagged

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import com.example.adaptivestreamingplayer.jetlagged.modal.sleepData
import com.example.adaptivestreamingplayer.ui.theme.SmallHeadingStyle
import com.example.adaptivestreamingplayer.ui.theme.Yellow
import com.example.adaptivestreamingplayer.ui.theme.YellowVariant
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

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
    Layout(
        contents = listOf(hoursHeader, dayLabels, sleepBars),
        modifier = Modifier.padding(bottom = 32.dp)
    ) { (hoursHeaderMeasurables, dayLabelMeasurables, barMeasureables), constraints ->
        require(hoursHeaderMeasurables.size == 1) {
            "hoursHeader should only emit one composable"
        }
        val hoursHeaderPlaceable = hoursHeaderMeasurables.first().measure(constraints)

        val totalHeight = hoursHeaderPlaceable.height

        val dayLabelPlaceables = dayLabelMeasurables.map { measurable -> measurable.measure(constraints) }

        val barPlaceables = barMeasureables.map { measurable ->
            val barParentData =
            measurable.measure(constraints)
        }

        layout(0,0) {

        }
    }
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

@LayoutScopeMarker
@Immutable
object TimeGraphScope {
    @Stable
    fun Modifier.timeGraphBar(
        start: LocalDateTime,
        end: LocalDateTime,
        hours: List<Int>,
    ): Modifier {
        val earliestTime = java.time.LocalTime.of(hours.first(), 0)
        val durationInHours = ChronoUnit.MINUTES.between(start, end) / 60f
        val durationFromEarliestToStartInHours =
            ChronoUnit.MINUTES.between(earliestTime, start.toLocalTime()) / 60f
        // we add extra half of an hour as hour label text is visually centered in its slot
        val offsetInHours = durationFromEarliestToStartInHours + 0.5f
        return then(
            TimeGraphParentData(
                duration = durationInHours / hours.size,
                offset = offsetInHours / hours.size
            )
        )
    }
}
class TimeGraphParentData(
    val duration: Float,
    val offset: Float,
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?) = this@TimeGraphParentData
}
