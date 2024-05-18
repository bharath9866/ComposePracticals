package com.example.adaptivestreamingplayer.phoneNumberValidation

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PhoneNumberValidationForUAETest {
    val UAE = "^(?:\\+971|00971)?(2|3|4|6|7|9|50|51|52|55|56)([0-9]{7})$"

    @Test
    fun `get phone number count without country code`() {
        val UAE = "^(?:\\+971|00971)?(2|3|4|6|7|9|50|51|52|55|56)([0-9]{7})$"

        val phoneNumberValidation = PhoneNumberValidation
        val result = phoneNumberValidation.getMaxPhoneNumberCount("501234567", UAE)

        println("1. get phone number count without country code: $result")
        assertThat(result).isEqualTo(9)
    }
    @Test
    fun `get phone number count with country code +971501234567`() {
        val UAE = "^(?:\\+971|00971)?(2|3|4|6|7|9|50|51|52|55|56)([0-9]{7})$"

        val phoneNumberValidation = PhoneNumberValidation
        val result = phoneNumberValidation.getMaxPhoneNumberCount("+971501234567", UAE)

        println("2. get phone number count with country code +971501234567: $result")
        assertThat(result).isEqualTo(9)
    }

    @Test
    fun `get phone number count with country code 00971501234567`() {
        val UAE = "^(?:\\+971|00971)?(2|3|4|6|7|9|50|51|52|55|56)([0-9]{7})$"

        val phoneNumberValidation = PhoneNumberValidation
        val result = phoneNumberValidation.getMaxPhoneNumberCount("00971501234567", UAE)

        println("3. get phone number count with country code 00971501234567: $result")
        assertThat(result).isEqualTo(9)
    }
    @Test
    fun `get phone number count without country code 41234567`() {
        val UAE = "^(?:\\+971|00971)?(2|3|4|6|7|9|50|51|52|55|56)([0-9]{7})$"

        val phoneNumberValidation = PhoneNumberValidation
        val result = phoneNumberValidation.getMaxPhoneNumberCount("41234567", UAE)

        println("4. get phone number count without country code 41234567: $result")
        assertThat(result).isEqualTo(8)
    }

    @Test
    fun `get phone number count with country code 00971`() {
        val UAE = "^(?:\\+971|00971)?(2|3|4|6|7|9|50|51|52|55|56)([0-9]{7})$"

        val phoneNumberValidation = PhoneNumberValidation
        val result = phoneNumberValidation.getMaxPhoneNumberCount("0097141234567", UAE)

        println("5. get phone number count with country code 00971: $result")
        assertThat(result).isEqualTo(8)
    }

    @Test
    fun `get phone number count with country code +97141234567`() {
        val UAE = "^(?:\\+971|00971)?(2|3|4|6|7|9|50|51|52|55|56)([0-9]{7})$"

        val phoneNumberValidation = PhoneNumberValidation
        val result = phoneNumberValidation.getMaxPhoneNumberCount("+97141234567", UAE)

        println("6. get phone number count with country code +97141234567: $result")
        assertThat(result).isEqualTo(8)
    }
}