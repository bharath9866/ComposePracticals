package com.example.adaptivestreamingplayer.jetlagged

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.adaptivestreamingplayer.jetlagged.modal.SleepGraphData
import com.example.adaptivestreamingplayer.jetlagged.modal.sleepData


@Composable
fun JetLaggedSleepGraphCard(
    sleepState: SleepGraphData,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableStateOf(SleepTab.Week) }
    BasicInformationCard(
        borderColor = MaterialTheme.colorScheme.primary,
        modifier = modifier
    ) {
        Column {
            HomeScreenCardHeading(text = "Sleep")
            JetLaggedHeaderTabs(
                onTabSelected = { selectedTab = it },
                selectedTab = selectedTab,
                modifier = Modifier.padding(top = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            JetLaggedTimeGraph(sleepState)
        }
    }
}

@Preview
@Composable
private fun JetLaggedSleepGraphCardPreview() {
    JetLaggedSleepGraphCard(
        SleepGraphData(sleepDayData = sleepData.sleepDayData)
    )
}