package com.example.adaptivestreamingplayer.textstreaming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class StreamingState(
    val displayedText: String = "",
    val isStreaming: Boolean = false,
    val isComplete: Boolean = false
)

class TextStreamingViewModel : ViewModel() {

    private val _streamingState = MutableStateFlow(StreamingState())
    val streamingState: StateFlow<StreamingState> = _streamingState.asStateFlow()

    private val sampleTexts = listOf(
        "Hello! I'm a text streaming assistant similar to ChatGPT. I can help you with various tasks and answer your questions.",
        "Text streaming creates a more engaging user experience by revealing content progressively, making it feel more conversational and natural.",
        "This implementation uses Jetpack Compose with StateFlow to manage the streaming state and update the UI efficiently.",
        "You can customize the streaming speed, add typing indicators, and even implement real API calls for actual AI responses."
    )

    fun startStreaming(message: String = "") {
        val textToStream = message.ifEmpty { sampleTexts.random() }

        viewModelScope.launch {
            _streamingState.value = StreamingState(
                displayedText = "",
                isStreaming = true,
                isComplete = false
            )

            textToStream.forEachIndexed { index, char ->
                delay(50) // Adjust speed as needed
                _streamingState.value = _streamingState.value.copy(
                    displayedText = textToStream.substring(0, index + 1)
                )
            }

            _streamingState.value = _streamingState.value.copy(
                isStreaming = false,
                isComplete = true
            )
        }
    }

    fun resetStream() {
        _streamingState.value = StreamingState()
    }
}