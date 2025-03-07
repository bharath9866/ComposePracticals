package com.example.adaptivestreamingplayer.coroutines

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import com.example.adaptivestreamingplayer.core.AppRoute
import com.example.adaptivestreamingplayer.coroutines.coroutineBasics.joinsAndDeferred.AsyncAwaitInCoroutine
import com.example.adaptivestreamingplayer.coroutines.coroutineBasics.joinsAndDeferred.ConcurrenceBasicIntroduction
import com.example.adaptivestreamingplayer.coroutines.coroutineBasics.joinsAndDeferred.JoinsInCoroutine
import com.example.adaptivestreamingplayer.utils.Button
import com.example.adaptivestreamingplayer.utils.ComposableLifecycle

@Preview
@Composable
fun CoroutineHomeScreen(
    navigateInCompose:(AppRoute) -> Unit = {}
) {
    val context = LocalContext.current
    ComposableLifecycle { _, event: Lifecycle.Event ->
        when(event) {
            Lifecycle.Event.ON_CREATE -> context.startActivity(Intent(context, ConcurrenceBasicIntroduction::class.java))
            else -> {}
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ItemBox(
            itemName = "Coroutine Basic Structured Concurrency",
            onClick = {
                context.startActivity(Intent(context, ConcurrenceBasicIntroduction::class.java))
            }
        )
        ItemBox(
            itemName = "Joins in Coroutine",
            onClick = {
                context.startActivity(Intent(context, JoinsInCoroutine::class.java))
            }
        )
        ItemBox(
            itemName = "Async Await In Coroutine",
            onClick = {
                context.startActivity(Intent(context, AsyncAwaitInCoroutine::class.java))
            }
        )
        ItemBox(
            itemName = "Coroutines In Compose",
            onClick = {
                navigateInCompose(AppRoute.CoroutineInCompose)
            }
        )
        ItemBox(
            itemName = "Coroutine Basics: [Easy] Assignment #1 (Birds)",
            onClick = {
                navigateInCompose(AppRoute.EasyAssignmentOne)
            }
        )
        ItemBox(
            itemName = "Coroutine Basics: [Medium] Assignment #2 (Birds)",
            onClick = {
                navigateInCompose(AppRoute.MediumAssignmentTwo)
            }
        )
        ItemBox(
            itemName = "Coroutine Basics: [Hard] Assignment #3 (Birds)",
            onClick = {
                navigateInCompose(AppRoute.HardAssignmentThree)
            }
        )
        ItemBox(
            itemName = "What is Coroutine Context?",
            onClick = {
                navigateInCompose(AppRoute.WhatIsCoroutineContextRoute)
            }
        )
        ItemBox(
            itemName = "With-Coroutine Context?",
            onClick = {
                navigateInCompose(AppRoute.WithCoroutineContextRoute)
            }
        )
        ItemBox(
            itemName = "Coroutine Context - [Easy] Assignment #1",
            onClick = {
                navigateInCompose(AppRoute.EasyAssignmentOneCoroutineContextRoute)
            }
        )
        ItemBox(
            itemName = "Coroutine Context - [Medium] Assignment #2",
            onClick = {
                navigateInCompose(AppRoute.MediumAssignmentTwoCoroutineContextRoute)
            }
        )
    }
}

@Preview
@Composable
private fun ItemBox(
    itemName: String = "",
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier.fillMaxWidth(),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = onClick,
        ) {
            Text(text = itemName)
        }
    }
}