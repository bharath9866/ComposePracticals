package com.example.adaptivestreamingplayer.jetlagged

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.adaptivestreamingplayer.jetlagged.modal.JetLaggedHomeScreenState

@Composable
fun JetLaggedScreen(
    modifier: Modifier = Modifier,
    viewModel: JetLaggedHomeScreenViewModel = viewModel()
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    JetLaggedScreen(
        uiState = uiState.value,
    )
}
@Composable
fun JetLaggedScreen(
    modifier: Modifier = Modifier,
    onDrawerClicked: () -> Unit = {},
    isFromPreview: Boolean = false,
    uiState: JetLaggedHomeScreenState
) {
    ScreenContent(
        modifier = modifier,
        onDrawerClicked = onDrawerClicked,
        isFromPreview = isFromPreview,
        uiState = uiState
    )
}

@Composable
private fun ScreenContent(
    modifier: Modifier = Modifier,
    onDrawerClicked: () -> Unit = {},
    isFromPreview: Boolean = false,
    uiState: JetLaggedHomeScreenState
) {
    Column(
        modifier = modifier
            .background(Color.White)
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Column(
            modifier = Modifier.then(
                if(isFromPreview)
                    Modifier.yellowBackground()
                else
                    Modifier.movingStripesBackground(
                        stripeColor = Color.White,
                        backgroundColor = Color.Black
                    )
            )
        ) {
            JetLaggedHeader(
                modifier = Modifier.fillMaxWidth(),
                onDrawerClicked = onDrawerClicked,
            )
            JetLaggedSleepSummary(modifier = Modifier)
            JetLaggedSleepGraphCard(sleepState = uiState.sleepGraphData, Modifier.widthIn(max = 600.dp))
        }
    }
}


@Preview
@Composable
private fun JetLaggedScreenPreview() {
    JetLaggedScreen(
        modifier = Modifier,
        onDrawerClicked = {},
        isFromPreview = true,
        uiState = JetLaggedHomeScreenState()
    )
}
