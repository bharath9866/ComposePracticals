package com.example.adaptivestreamingplayer.phoneNumberValidation

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PhoneNumberValidationForIndiaTest {
    private val INDIA = "^(\\+91|\\+91-|0)?[789]\\d{9}$"
    @Test
    fun `get phone number count without country code`() {

        val phoneNumberValidation = PhoneNumberValidation
        val result = phoneNumberValidation.getMaxPhoneNumberCount("9866062197", INDIA)

        println("1. get phone number count without country code: $result")
        assertThat(result).isEqualTo(10)
    }
    @Test
    fun `get phone number count with country code +919866062197`() {

        val phoneNumberValidation = PhoneNumberValidation
        val result = phoneNumberValidation.getMaxPhoneNumberCount("+919866062197", INDIA)

        println("2. get phone number count with country code +971501234567: $result")
        assertThat(result).isEqualTo(10)
    }
    @Test
    fun `get phone number count with country code 009919866062197`() {

        val phoneNumberValidation = PhoneNumberValidation
        val result = phoneNumberValidation.getMaxPhoneNumberCount("0919866062197", INDIA)

        println("3. get phone number count with country code 00971501234567: $result")
        assertThat(result).isEqualTo(10)
    }
}