package com.example.adaptivestreamingplayer.displayTextCollection

import android.util.Log
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.adaptivestreamingplayer.utils.toArrayList

@Preview
@Composable
private fun SupportMultipleLinksInASingleStringOfText() {
    val annotatedText = buildAnnotatedString {
        append("Go to ")

        // We attach this *URL* annotation to the following content until `pop()` is called.
        pushStringAnnotation(
            tag = "URL", annotation = "https://developer.android.com"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Green, fontWeight = FontWeight.Bold
            )
        ) {
            append("Android Developers")
        }
        pop()

        append(" and check the ")

        pushStringAnnotation(
            tag = "URL", annotation = "https://developer.android.com/jetpack/compose"
        )
        withStyle(
            style = SpanStyle(
                color = Color.Blue, fontWeight = FontWeight.Bold
            )
        ) {
            append("Compose guidelines.")
        }
        pop()
    }
    ClickableText(text = annotatedText, onClick = { offset ->
        annotatedText.getStringAnnotations(
            tag = "URL", start = offset, end = offset
        ).firstOrNull()?.let { annotation ->
            // If yes, we log its value.
            Log.d("Clicked URL", annotation.item)
        }
    })
}

fun main() {
    val currentVersion = 240512
    val version = 240511

    val versionList = arrayListOf(AppVersion(240510, false), AppVersion(240511, false), AppVersion(240512, true), AppVersion(240513, false))
    val startIndex = versionList.indexOfFirst {  it.currentVersion == currentVersion }


    val subList = if(startIndex>=0) versionList.subList(startIndex, versionList.size) else arrayListOf()
    val versionCode = subList.lastOrNull()?.currentVersion?:version
    val isAForceUpdate = subList.any {it.mandatoryVersion}

    println(startIndex)
    println(subList)
    println(versionCode)
    println(isAForceUpdate)
}

data class AppVersion(
    var currentVersion: Int,
    var mandatoryVersion: Boolean = false
)