package com.example.adaptivestreamingplayer.customComponent.models

import com.google.gson.annotations.SerializedName

sealed class SpinnerData

data class CountryCodeResponse(
    @SerializedName("statusCode") var statusCode: Int? = null,
    @SerializedName("message") var message: String? = null,
    @SerializedName("data") var countryDetails: ArrayList<CountryDetail> = arrayListOf()
)
data class CountryDetail(
    @SerializedName("label") var label: String="",
    @SerializedName("code") var regionCode: String="",
    @SerializedName("phone") var countryCode: String="",
    @SerializedName("phoneLength") var phoneLength: ArrayList<Int> = arrayListOf(),
    @SerializedName("logo") var logo: String="",
    @SerializedName("flag") var flag: String="",
    @SerializedName("unicode") var unicode: String=""
): SpinnerData() {
  val countryCodeWithPlus:String
      get() = "+$countryCode"
}
data class CountryDetailTwo(
    @SerializedName("label") var label: String="",
    @SerializedName("code") var regionCode: String="",
    @SerializedName("phone") var countryCode: String="",
    @SerializedName("phoneLength") var phoneLength: ArrayList<Int> = arrayListOf(),
    @SerializedName("logo") var logo: String="",
    @SerializedName("flag") var flag: String="",
    @SerializedName("unicode") var unicode: String=""
): SpinnerData() {
    val countryCodeWithPlus:String
        get() = "+$countryCode"
}