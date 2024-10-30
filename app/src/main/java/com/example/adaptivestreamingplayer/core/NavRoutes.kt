package com.example.adaptivestreamingplayer.core

import kotlinx.serialization.Serializable

@Serializable
data object HomeRoute

@Serializable
data object ComposeVideoPlayerRoute

@Serializable
data object JetLaggedRoute

@Serializable
data object CloudFrontScreenRoute

@Serializable
data object PlaylistScreenRoute

@Serializable
data object OrderAppRoute

@Serializable
data object ComposeWebView

@Serializable
data object ChainingAnimation

@Serializable
data object ItemPlacement

@Serializable
data object Flashcards

@Serializable
data object Vernacular

@Serializable
data class NavigationOne(val id: Int? = null, val name: String? = null)

@Serializable
data class NavigationTwo(val id: Int? = null, val name: String? = null)

@Serializable
data object ComposeCanvasIcons

@Serializable
data object ScrollArea

@Serializable
data object DialogScreen

@Serializable
data object CreatePlan

@Serializable
data object ComposeToAndroidView