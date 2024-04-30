package com.example.adaptivestreamingplayer.ilts.report

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object ConvertFromJsonToKeyValue {
    fun stringToMapOfMap(str:String): Map<String, String>? {
        return try {

            val gson = Gson()
            val mapType = object : TypeToken<Map<String, String>>() {}.type
            gson.fromJson(str.removeQuestionQuotas(), mapType)
        } catch (e :Exception) {
            Log.d("mappingStringToMapOfMap", "${e.message}")
            null
        }
    }

    private fun String.removeQuestionQuotas(): String {
        return this.replace("\\n", "").replace("\\\"", "\"").trim()
    }
}