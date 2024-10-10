@file:OptIn(ExperimentalFoundationApi::class)

package com.example.adaptivestreamingplayer

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.tooling.preview.Preview

fun main() {
    methodOne()
}

fun methodOne() {
    val startAt = System.nanoTime()
    println("Inside Method #1")
    methodTwo(1L, "bharath")
    val timeTaken = System.nanoTime() - startAt
    println("Method 1 took $timeTaken nano seconds")
}

fun methodTwo(long: Long, string:String?) {
    val aliasLong = long
    val aliasString = string
    println("Inside Method #2 $aliasLong $aliasString")
    if(long == 0L || string == null) return
    methodThree()
}

fun methodThree() {
    println("Inside Method #3")
}

@Preview
@Composable
fun TextWithBasicTextField(modifier: Modifier = Modifier) {
    val name = rememberSaveable(saver = TextFieldState.Saver) {
        TextFieldState("Bharath", TextRange(1))
    }
}