package com.example.adaptivestreamingplayer.jetlagged

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import com.example.adaptivestreamingplayer.core.JetLaggedRoute

@Preview
@Composable
private fun JetLaggedScreenPreview() {
    JetLaggedScreen(
        modifier = Modifier,
        onDrawerClicked = {},
        isFromPreview = true
    )
}

@Composable
fun JetLaggedScreen(
    modifier: Modifier = Modifier,
    onDrawerClicked: () -> Unit = {},
    isFromPreview: Boolean = false
) {
    ScreenContent(
        modifier = modifier,
        onDrawerClicked = onDrawerClicked,
        isFromPreview = isFromPreview
    )
}

@Composable
private fun ScreenContent(
    modifier:Modifier = Modifier,
    onDrawerClicked: () -> Unit = {},
    isFromPreview: Boolean = false
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
        }
    }
}