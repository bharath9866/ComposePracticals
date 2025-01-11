package com.example.adaptivestreamingplayer.jetlagged

import androidx.lifecycle.ViewModel
import com.example.adaptivestreamingplayer.jetlagged.modal.JetLaggedHomeScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class JetLaggedHomeScreenViewModel : ViewModel() {

    val uiState: StateFlow<JetLaggedHomeScreenState> = MutableStateFlow(JetLaggedHomeScreenState())
}
