package com.example.adaptivestreamingplayer.filterChip

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
private fun TopicChipPreview() {
    TopicChip(
        topic = "Android",
        onCancel = {}
    )
}

@Composable
fun TopicChip(
    modifier: Modifier = Modifier,
    topic:String,
    onCancel:() -> Unit = {}
) {
    FilterChip(
        modifier = modifier,
        selected = true,
        onClick = {},
        shape = MaterialTheme.shapes.large,
        colors = FilterChipDefaults.filterChipColors()
            .copy(selectedContainerColor = Color(0xFFEEF0FF)),
        leadingIcon = {
            Icon(
                Icons.Outlined.Info,
                modifier = Modifier.size(16.dp),
                contentDescription = null,
                tint = Color.DarkGray,
            )
        },
        trailingIcon = {
            Icon(
                imageVector = Icons.Outlined.Close,
                modifier = Modifier
                    .size(16.dp)
                    .clickable { onCancel() },
                contentDescription = "Cancel",
                tint = Color.DarkGray,
            )
        },
        label = {
            Text(
                text = topic,
                style = MaterialTheme.typography.bodySmall,
                color = Color(0xFF40434F),
                textAlign = TextAlign.Center,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        }
    )
}

@Composable
fun TopicDropdown(
    modifier: Modifier = Modifier,
    selectedTopics: Set<String>,
    onSelectedTopicsChange: (Set<String>) -> Unit,
    onAddingTopicChange: (Boolean) -> Unit,
    searchQuery: String,
    onSearchQueryChange: (String) -> Unit,
    savedTopics: Set<String>,
    onSavedTopicsChange: (Set<String>) -> Unit = {},
) {

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .offset(y = -(8.dp))
            .shadow(elevation = 8.dp, shape = MaterialTheme.shapes.medium),
    ) {

        Column(
            modifier = modifier
                .fillMaxWidth()
                .background(color = Color.White)
        ) {

            // Filter saved topics based on search query
            val matchingSavedTopics = savedTopics.filter {
                it.startsWith(prefix = searchQuery, ignoreCase = true) && !selectedTopics.contains(it)
            }

            // Show matching saved topics first
            matchingSavedTopics.forEach { topic ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            onClick = {
                                onSelectedTopicsChange(selectedTopics + topic)
                                onSearchQueryChange("")
                                onAddingTopicChange(false)
                            }
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Text(
                        text = topic,
                        style = MaterialTheme.typography.titleSmall,
                        color = Color(0xFF40434F),
                    )
                }
            }

            // Show create option if query doesn't exist in saved topics
            if (!savedTopics.any { it.equals(searchQuery, ignoreCase = true) }) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            val newTopic = searchQuery.trim()
                            onSavedTopicsChange(savedTopics + newTopic)
                            onSelectedTopicsChange(selectedTopics + newTopic)
                            onSearchQueryChange("")
                            onAddingTopicChange(false)
                        }
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        modifier = Modifier.size(12.dp),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                    )

                    Text(
                        text = "Create $searchQuery",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Black,
                    )
                }
            }
        }
    }
}