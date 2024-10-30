package com.example.adaptivestreamingplayer.slThree

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetTextI18n")
@Composable
fun ComposeToAndroidView(modifier: Modifier = Modifier) {
    var flag by remember { mutableIntStateOf(0) }
    AndroidView(
        factory = { it ->
            TextView(it).apply {
                text = "Testing Rendering Android View from Compose"
                setOnClickListener {
                    flag++
                }
            }
        },
        update = {
            it.text = "Update Rendering Android View from Compose: $flag"
        },
        modifier = Modifier,
        onReset = {},
        onRelease = {}
    )
}