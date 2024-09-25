package com.example.adaptivestreamingplayer.core

import androidx.compose.runtime.Stable

@Stable
data class NavScreenActions(
    val navigateToILTSReports: () -> Unit = {},
    val navigateToLogin: () -> Unit = {},
    val navigateToVideoPlayer: () -> Unit = {},
    val navigateToMemoryCard: () -> Unit = {},
    val navigateToProgressButton: () -> Unit = {}
)