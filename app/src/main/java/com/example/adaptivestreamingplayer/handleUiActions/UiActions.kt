package com.example.adaptivestreamingplayer.handleUiActions

import androidx.compose.runtime.Stable

internal interface UiAction
internal typealias OnAction = (UiAction) -> Unit


@Stable
internal data class MainScreenActions(
    val commonActions: CommonActions,
    val childComponentActions: ChildComponentActions = ChildComponentActions(),
    val secondChildComponentActions: SecondChildComponentActions = SecondChildComponentActions(),
)

@Stable
internal data class CommonActions(
    val retry: () -> Unit = {},
    val updateUsername: (username: String) -> Unit = {},
    // + 10 actions
)

@Stable
internal data class ChildComponentActions(
    val navigateToSecondScreen: () -> Unit = {},
    // + 20 actions
)

@Stable
internal data class SecondChildComponentActions(
    val navigateToSearch: () -> Unit = {},
    // + 15 actions
)
