@file:OptIn(ExperimentalLayoutApi::class)

package com.example.adaptivestreamingplayer.filterChip

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.adaptivestreamingplayer.utils.PreviewDevices

@PreviewDevices
@Composable
fun FilterChipDropDown(
    modifier: Modifier = Modifier,
) {
    var selectedTopics by remember { mutableStateOf(setOf<String>()) }
    var savedTopics by remember { mutableStateOf(setOf("Work", "Hobby", "Personal", "Office", "Workout")) }
    var isAddingTopic by remember { mutableStateOf(false) }
    var searchQuery by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }


    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopicsList(
            modifier = modifier,
            isAddingTopic = isAddingTopic,
            onAddingTopicChange = { isAddingTopic = it },
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            keyboardController = keyboardController,
            focusRequester = focusRequester,
            onSelectedTopics = { selectedTopics = it },
            selectedTopics = selectedTopics
        )

        if (searchQuery.isEmpty()) return

        TopicDropdown(
            modifier = modifier,
            selectedTopics = selectedTopics,
            onSelectedTopicsChange = { selectedTopics = it },
            onAddingTopicChange = { isAddingTopic = it },
            searchQuery = searchQuery,
            onSearchQueryChange = { searchQuery = it },
            savedTopics = savedTopics,
            onSavedTopicsChange = { savedTopics = it },
        )
    }
}

@Composable
private fun TopicsList(
    modifier: Modifier = Modifier,
    isAddingTopic: Boolean,
    onAddingTopicChange: (Boolean) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusRequester: FocusRequester,
    selectedTopics: Set<String>,
    onSelectedTopics: (Set<String>) -> Unit,
) {
    Surface(
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(4.dp),
                spotColor = Color(0xFF474F60).copy(alpha = 0.08f)
            ),
        color = Color(0xFFD9E2FF),
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            TitleText()
            ChipFlowRow(
                isAddingTopic = isAddingTopic,
                onAddingTopicChange = onAddingTopicChange,
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                keyboardController = keyboardController,
                focusRequester = focusRequester,
                selectedTopics = selectedTopics,
                onSelectedTopics = onSelectedTopics
            )
        }
    }
}

@Composable
private fun TitleText() {
    Text(
        text = "Topics",
        style = MaterialTheme.typography.titleMedium,
        color = Color(0xFF191A20),
    )
}


@Composable
private fun ChipFlowRow(
    modifier: Modifier = Modifier,
    isAddingTopic: Boolean,
    onAddingTopicChange: (Boolean) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    keyboardController: SoftwareKeyboardController?,
    focusRequester: FocusRequester,
    selectedTopics: Set<String>,
    onSelectedTopics: (Set<String>) -> Unit,
) {
    LaunchedEffect(isAddingTopic) {
        // Don't show keyboard when not adding a topic
        if (isAddingTopic.not()) return@LaunchedEffect
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    FlowRow(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center
    ) {
        selectedTopics.forEach { topic ->
            TopicChip(
                modifier = Modifier.padding(horizontal = 8.dp),
                topic = topic,
                onCancel = { onSelectedTopics(selectedTopics - topic) }
            )
        }
        if (isAddingTopic.not()) {
            IconButton(
                onClick = { onAddingTopicChange(true) },
                modifier = modifier
                    .padding(vertical = 8.dp)
                    .background(color = Color(0xFFEEF0FF), shape = CircleShape)
                    .size(32.dp),
                content = {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = null,
                        tint = Color(0xFF40434F),
                    )
                }
            )
        } else {
            BasicTextField(
                value = searchQuery,
                onValueChange = { onSearchQueryChange(it) },
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(top = 16.dp)
                    .focusRequester(focusRequester),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Done,
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        onSelectedTopics(selectedTopics + searchQuery)
                        onAddingTopicChange(false)
                        onSearchQueryChange("")
                        keyboardController?.hide()
                    }
                )
            )
        }
    }
}
