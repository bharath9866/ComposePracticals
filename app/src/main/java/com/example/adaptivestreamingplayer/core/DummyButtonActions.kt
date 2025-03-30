package com.example.adaptivestreamingplayer.core

import androidx.compose.runtime.Stable

@Stable
data class DummyButtonActions(
    val testingILScreenActions: TestingILScreenActions = TestingILScreenActions(),
    val experimentalScreenAction: ExperimentalScreenActions = ExperimentalScreenActions()
) {
    data class TestingILScreenActions(
        val navigateToILTSReport: () -> Unit = {},
        val navigateToVideoPlayer: () -> Unit = {},
        val navigateToMemoryCard: () -> Unit = {},
        val navigateToComposePlayer: () -> Unit = {},
        val navigateToLogin: () -> Unit = {},
        val navigateToPlaylist: () -> Unit = {},
        val navigateToCloudFront: () -> Unit = {},
        val navigateToProgressButton: () -> Unit = {},
        val navigateToVernacular: () -> Unit = {},
    )

    data class ExperimentalScreenActions(
        val navigateToItemPlacement: () -> Unit = {},
        val navigateToChainingAnimation: () -> Unit = {},
        val navigateToJetLagged: () -> Unit = {},
        val navigateToOrderApp: () -> Unit = {},
        val navigateToTypeSafeNavigation: (id:Int?, name:String?) -> Unit = { id, name -> },
        val navigateToComposeCanvasIcons: () -> Unit = {},
        val navigateToScrollArea: () -> Unit = {},
        val navigateToDialogScreen: () -> Unit = {},
        val navigateToCustomSpinner: () -> Unit = {},
        val navigateToPageFlip: () -> Unit = {},
        val navigateToCreatePlan: () -> Unit = {},
        val navigateToCreatePlanActivity: () -> Unit = {},
        val navigateToRenderAndroidViewInCompose: () -> Unit = {},
        val navigateToFaceBookMainActivity: () -> Unit = {},
        val navigateToChatReactionActivity: () -> Unit = {},
        val navigateToHomeWidgetList: () -> Unit = {},
        val navigateToCoroutineScreen: () -> Unit = {},
        val navigateToFilterChip: () -> Unit = {},
    )
}