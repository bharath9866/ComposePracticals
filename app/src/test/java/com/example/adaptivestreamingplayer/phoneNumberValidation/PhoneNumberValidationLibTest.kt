package com.example.adaptivestreamingplayer.phoneNumberValidation

import com.google.common.truth.Truth.assertThat
import com.google.i18n.phonenumbers.PhoneNumberUtil
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberType
import com.google.i18n.phonenumbers.Phonenumber
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber
import org.junit.Before
import org.junit.Test


class PhoneNumberValidationLibTest {
    private lateinit var phoneNumberUtil: PhoneNumberUtil

    private val UAE_COUNTRY_CODE = 971
    private val INDIA_COUNTRY_CODE = 91

    val INDIAN_PHONE_REGEX = "^(\\+91|\\+91-|0)?[789]\\d{9}\$"
    val UAE_PHONE_REGEX = "^(?:\\+971|00971)?(?:2|3|4|6|7|9|50|51|52|55|56)[0-9]{7}\$"

    @Before
    fun setup() {
        phoneNumberUtil = PhoneNumberUtil.getInstance()
    }

    @Test
    fun `UAE mobile number with 9 digits is valid`() {
        val number = PhoneNumber().setCountryCode(UAE_COUNTRY_CODE).setNationalNumber(501234567L)
        assertThat(phoneNumberUtil.isValidNumberForRegion(number, "AE")).isEqualTo(true)
    }
    @Test
    fun `UAE mobile number with 8 digits with start digit of 5 is inValid`() {
        val number = PhoneNumber().setCountryCode(UAE_COUNTRY_CODE).setNationalNumber(50123456L)
        assertThat(phoneNumberUtil.isValidNumberForRegion(number, "AE")).isEqualTo(false)
    }
    @Test
    fun `UAE mobile number with 8 digits with start digit of 4 is valid`() {
        val number = PhoneNumber().setCountryCode(UAE_COUNTRY_CODE).setNationalNumber(41234567L)
        //val i = phoneNumberUtil.isValidNumberForRegion(number, "AE")
        val result = phoneNumberUtil.isPossibleNumberForType(number, PhoneNumberType.MOBILE)
        assertThat(result).isEqualTo(true)
    }

    @Test
    fun `INDIA mobile number with 10 digits with start digit of 9, 4, 8, 7 is valid`() {
        val listOfNumbers =
            listOf(
                9876543219L,
                4041975694L,
                8041975694L,
                7041975694L,
                9448376473L,
                18004253800L,
                2942424447L,
                5876543219L
            )
        listOfNumbers.forEach {
            val number = PhoneNumber().setCountryCode(INDIA_COUNTRY_CODE).setNationalNumber(it)
            assertThat(phoneNumberUtil.isValidNumberForRegion(number, "IN")).isEqualTo(true)
        }
    }

    @Test
    fun `INDIA mobile number with 9 digits is inValid`() {
        val number = PhoneNumber().setCountryCode(INDIA_COUNTRY_CODE).setNationalNumber(987654321L)
        assertThat(phoneNumberUtil.isValidNumberForRegion(number, "IN")).isEqualTo(false)
    }

    @Test
    fun `mobile number max digit`(){

        val regionCode = "IN"

        val exampleNumber = phoneNumberUtil.getExampleNumberForType(
            regionCode,
            PhoneNumberUtil.PhoneNumberType.PERSONAL_NUMBER
        )

        val maxLength: Int = phoneNumberUtil.getLengthOfNationalDestinationCode(exampleNumber)
        println("Maximum digits required for phone number: $maxLength")

        assert(maxLength == 10)
    }

    @Test
    fun `testing isValidNumber`() {
        val list = listOf("+97151234567")
        list.forEach {
            assertThat(
                phoneNumberUtil.isValidNumber(phoneNumberUtil.parse(it, "AE"))
            ).isEqualTo(true)
        }
    }

    @Test
    fun `Validate 501234567`() {
        val phoneNumberStr = "501234567"
        val phoneNumber = phoneNumberUtil.parse(phoneNumberStr, "AE")
        val result = phoneNumberUtil.isValidNumberForRegion(phoneNumber, "AE")
        println(result)
        assert(result)
    }
    @Test
    fun `Validate 41234567`() {
        val phoneNumberStr = "41234567"
        val phoneNumber = phoneNumberUtil.parse(phoneNumberStr, "AE")
        val result = phoneNumberUtil.isValidNumberForRegion(phoneNumber, "AE")
        println(result)
        assert(result)
    }

    @Test
    fun `Validate List of UAE Numbers using Regular Expression`() {
        val phoneNumberValidation = PhoneNumberValidation

        val listOfPhoneNumber = listOf("21234567","31234567","41234567", "501234567","551234567", "61234567", "71234567", "91234567")
        listOfPhoneNumber.forEach {
            val result =phoneNumberValidation.isValidPhone(it, UAE_PHONE_REGEX)
            assertThat(result).isEqualTo(true)
        }
    }

    @Test
    fun `Validate UAE Number using Regular Expression`() {
        val phoneNumberValidation = PhoneNumberValidation
        val phoneNumber = "501234567"
        assertThat(phoneNumberValidation.isValidPhone(phoneNumber, UAE_PHONE_REGEX)).isEqualTo(true)
    }

    @Test
    fun `Exploring PhoneNumberUtils class`() {
        val number = PhoneNumber().setCountryCode(UAE_COUNTRY_CODE).setNationalNumber(41234567L)
        //val result = phoneNumberUtil.isPossibleNumberForType(number, PhoneNumberType.MOBILE)
        val result = phoneNumberUtil.isValidNumberForRegion(number, "971")
       println(result)
        //assertThat(result).isEqualTo(true)
    }

    @Test
    fun `Testing with initial Zero`() {
        val number = PhoneNumber().setCountryCode(UAE_COUNTRY_CODE).setNationalNumber(0L)
        //val result = phoneNumberUtil.isPossibleNumberForType(number, PhoneNumberType.MOBILE)
        val result = phoneNumberUtil.isValidNumberForRegion(number, "971")
        println(result)
    }
}