@file:OptIn(ExperimentalFoundationApi::class)

package com.example.adaptivestreamingplayer.composeUnstyledPracticals

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.composables.core.ScrollArea
import com.composables.core.Thumb
import com.composables.core.ThumbVisibility
import com.composables.core.VerticalScrollbar
import com.composables.core.rememberScrollAreaState
import kotlin.time.Duration.Companion.seconds

@Preview
@Composable
fun ScrollAreaScreen() {
    val lazyListState = rememberLazyListState()
    val state = rememberScrollAreaState(lazyListState)

    ScrollArea(
        state = state,
        modifier = Modifier.background(Color.White)
    ) {
        LazyColumn(
            state = lazyListState,
            modifier = Modifier.padding(end = 8.dp).fillMaxSize(),
        ) {
            items(100) {
                ScrollItem(it.toString())
            }
        }
        VerticalScrollbar(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .fillMaxHeight()
                .width(8.dp)
        ) {
            Thumb(
                modifier = Modifier
                .clip(CircleShape)
                .background(Color.LightGray),
                thumbVisibility = ThumbVisibility.HideWhileIdle(
                    enter = fadeIn(),
                    exit = fadeOut(),
                    hideDelay = 2.seconds
                )
            )
        }
    }
}

@Preview
@Composable
private fun ScrollItem(itemName: String = "") {
    Row(
        modifier = Modifier
            .border(width = 4.dp, shape = RoundedCornerShape(90.dp), color = Color.Blue)
            .clip(RoundedCornerShape(90.dp))
            .fillMaxWidth()
            .height(50.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = itemName,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
    }
}