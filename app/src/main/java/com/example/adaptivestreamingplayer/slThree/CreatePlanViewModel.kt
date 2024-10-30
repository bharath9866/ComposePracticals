package com.example.adaptivestreamingplayer.slThree

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds


class CreatePlanViewModel : ViewModel() {
    private val _cardData = MutableStateFlow<CreatePlan?>(null)
    val cardData = _cardData.asStateFlow()

    fun callCardData() {
        viewModelScope.launch {
            _cardData.value = null
            delay(2.seconds)
            _cardData.value = createPlan
        }
    }

    fun incrementValue(value:Int) {
        viewModelScope.launch {
            _cardData.update {
                it?.copy(incrementValue = value+1)
            }
        }
    }

}

val createPlan = CreatePlan(
    studentName = "Bharath",
    descriptionOne = "Take the first step to your goal today",
    cardTitle = "Ready, Set Excel !",
    cardDescription = "Step closer to your ultimate goal of cracking JEE."
)

data class CreatePlan(
    val studentName:String,
    val descriptionOne:String,
    val cardTitle:String,
    val cardDescription:String,
    var incrementValue:Int = 1
)