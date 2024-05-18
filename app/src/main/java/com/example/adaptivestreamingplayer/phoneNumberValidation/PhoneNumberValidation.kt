package com.example.adaptivestreamingplayer.phoneNumberValidation

import android.content.Context
import java.util.regex.Pattern

fun main() {
    val INDIA = "^(\\+91|\\+91-|0)?[789]\\d{9}$"
    val UAE = "^(?:\\+971|00971)?(2|3|4|6|7|9|50|51|52|55|56)([0-9]{7})$"
    val regex = Regex(INDIA)

    val phoneNumber = "+919866062197"
    val matchResult = regex.find(phoneNumber)

    if (matchResult != null) {
        val areaCode = matchResult.groups[1]?.value
        //val maxSubsequentDigits = matchResult.groups[2]?.value

        matchResult.groups.forEach {
            println(it?.value)
        }

        println("Area Code: ${areaCode}")
        //println("Maximum Subsequent Digits Length: ${maxSubsequentDigits?.length}")
        //println("TotalLength: ${(areaCode?.length?:0)+(maxSubsequentDigits?.length?:0)}")
    } else {
        println("The phone number is not in the specified format.")
    }
}

object PhoneNumberValidation {
    fun validatePhoneNumber(phoneNumber: String, countryRegex: String): Boolean {
        val phoneNumber1 = phoneNumber.replace("-", "")
        if (phoneNumber1.isEmpty())
            return false
        else if (!Pattern.matches(countryRegex, phoneNumber1))
            return false
        return true
    }

    fun getMaxPhoneNumberCount(phoneNumber: String, countryRegex: String): Int {
        val matchResult = Regex(countryRegex).find(phoneNumber)
        return when(countryRegex){
            UAE -> {
                val areaCode = matchResult?.groups?.get(1)?.value?.length
                val numberRange = matchResult?.groups?.get(2)?.value?.length
                (areaCode?:0)+(numberRange?:0)
            }
            INDIA -> 9
            else -> 9
        }
    }

    fun isValidPhone(phoneNumber: String, regularExpression:String): Boolean {

        val phoneNumber1 = phoneNumber.replace("-","")

        if (phoneNumber1.isEmpty()) {
            return false
        } else if (!Pattern.matches(regularExpression, phoneNumber1)) {
            return false
        }
        return true
    }

}

val UAE = "^(?:\\+971|00971)?(2|3|4|6|7|9|50|51|52|55|56)([0-9]{7})$"
private val INDIA = "^(\\+91|\\+91-|0)?[789]\\d{9}$"