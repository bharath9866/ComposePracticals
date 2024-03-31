package com.example.adaptivestreamingplayer.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn

class SearchBarViewModel : ViewModel() {
    private val listStr = arrayListOf("video", "topic summaries")
    val seconds: SharedFlow<String> = flow {
        while(true)
            listStr.forEach {
                emit(it)
            }
    }.onEach { delay(1000) }
        .shareIn(viewModelScope, SharingStarted.Lazily)
}

fun ArrayList<String>.infiniteLoopOfList(coroutineScope: CoroutineScope): SharedFlow<String> {
    return flow {
        while(true)
            this@infiniteLoopOfList.forEach {
                emit(it)
            }
    }.onEach {
        delay(1000)
    }.shareIn(coroutineScope, SharingStarted.Eagerly)
}