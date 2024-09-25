package com.example.adaptivestreamingplayer.handleUiActions

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

@Composable
internal fun MainScreen(
    viewModel: MainScreenViewModel,
    navigateToSecondScreen: () -> Unit,
    navigateToSearch: () -> Unit,
) {
    val actions = MainScreenActions(
        commonActions = CommonActions(
            retry = viewModel::retry,
            updateUsername = viewModel::updateUsername,
        ),
        childComponentActions = ChildComponentActions(
            navigateToSecondScreen = navigateToSecondScreen,
        ),
        secondChildComponentActions = SecondChildComponentActions(
            navigateToSearch = navigateToSearch,
        ),
    )
    val state by viewModel.uiState.collectAsState()

    MainScreenScaffold(
        actions = actions,
        state = state,
    )
}

@Composable
internal fun MainScreenScaffold(
    actions: MainScreenActions,
    state: MainScreenState,
) {
    ChildComponent(
        actions = actions.childComponentActions,
    )
    SecondChildComponent(
        actions = actions.secondChildComponentActions,
        commonActions = actions.commonActions,
    )
}

@Composable
private fun ChildComponent(
    actions: ChildComponentActions,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = {
            actions.navigateToSecondScreen()
        },
        content = {
            Text(text = "Navigate To Second Screen")
        }
    )
}

@Composable
private fun SecondChildComponent(
    actions: SecondChildComponentActions,
    commonActions: CommonActions,
    modifier: Modifier = Modifier,
) {
    Button(
        onClick = {
            actions.navigateToSearch()
        },
        content = {
            Text(text = "Navigate To Second Screen")
        }
    )
    Button(
        onClick = {
            commonActions.updateUsername("username")
        },
        content = {
            Text(text = "Navigate To Second Screen")
        }
    )
}