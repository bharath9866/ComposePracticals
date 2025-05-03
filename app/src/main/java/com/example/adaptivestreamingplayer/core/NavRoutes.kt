package com.example.adaptivestreamingplayer.core

import kotlinx.serialization.Serializable

@Serializable
sealed class AppRoute {
    @Serializable
    data object HomeRoute : AppRoute()

    @Serializable
    data object ComposeVideoPlayerRoute : AppRoute()

    @Serializable
    data object JetLaggedRoute : AppRoute()

    @Serializable
    data object CloudFrontScreenRoute : AppRoute()

    @Serializable
    data object PlaylistScreenRoute : AppRoute()

    @Serializable
    data object OrderAppRoute : AppRoute()

    @Serializable
    data object ComposeWebView : AppRoute()

    @Serializable
    data object ChainingAnimation : AppRoute()

    @Serializable
    data object ItemPlacement : AppRoute()

    @Serializable
    data object Flashcards : AppRoute()

    @Serializable
    data object Vernacular : AppRoute()

    @Serializable
    data class NavigationOne(val id: Int? = null, val name: String? = null) : AppRoute()

    @Serializable
    data class NavigationTwo(val id: Int? = null, val name: String? = null) : AppRoute()

    @Serializable
    data object ComposeCanvasIcons : AppRoute()

    @Serializable
    data object ScrollArea : AppRoute()

    @Serializable
    data object DialogScreen : AppRoute()

    @Serializable
    data object CreatePlan : AppRoute()

    @Serializable
    data object ComposeToAndroidView : AppRoute()

    @Serializable
    data object SleepBarRoute : AppRoute()

    @Serializable
    data object StrokeHomeRoute : AppRoute()

    @Serializable
    data object StrokeTextRoute : AppRoute()

    @Serializable
    data object StrokeBrushRoute : AppRoute()

    @Serializable
    data object CoroutineScreenRoute : AppRoute()

    @Serializable
    data object CoroutineInCompose : AppRoute()

    @Serializable
    data object EasyAssignmentOne : AppRoute()

    @Serializable
    data object MediumAssignmentTwo : AppRoute()

    @Serializable
    data object HardAssignmentThree : AppRoute()

    @Serializable
    data object WhatIsCoroutineContextRoute : AppRoute()

    @Serializable
    data object WithCoroutineContextRoute : AppRoute()

    @Serializable
    data object EasyAssignmentOneCoroutineContextRoute : AppRoute()

    @Serializable
    data object MediumAssignmentTwoCoroutineContextRoute : AppRoute()

    @Serializable
    data object FilterChipDropDownRoute : AppRoute()

    @Serializable
    data object NotificationScreenRoute : AppRoute()
}