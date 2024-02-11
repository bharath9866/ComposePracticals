package com.example.adaptivestreamingplayer

import kotlin.math.roundToInt

fun main(){
    var duration:String? = "1163.349333"
    var durationinsecs: Int = try {
        duration = if (duration == null || duration == "null" || duration == "" || duration == " " || duration == "\" \"") "0" else duration
        val num = duration?.replace(Regex("[^.0-9]"), "")
        if (duration?.contains("minutes") == true)
            (num?.toInt() ?: 0) * 1
        else if (duration?.contains("hours") == true)
            (num?.toInt() ?: 0) * 60 * 60
        (num?.toFloat()?.roundToInt() ?: 0)
    } catch (e: Exception) {
        try {
            duration?.split(" ")?.first()?.toInt()?:0
        } catch (e: Exception) {
            duration?.filter { it.isDigit() }?.toInt() ?: 0
        }
    }

}