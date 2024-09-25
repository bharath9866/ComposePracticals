package com.example.adaptivestreamingplayer.flashcards.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.adaptivestreamingplayer.flashcards.ui.components.appBar.QuestionsAppBar

@Preview
@Composable
private fun FlashcardScreen() {
    QuestionsAppBar(
        isLinearProgressEnabled = false
    )
}