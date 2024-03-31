package com.example.adaptivestreamingplayer.ktor.dto

import com.google.gson.annotations.SerializedName

data class LoginRequest(
    //val uid: String? = null,
    //val userTypeId: Int? = 5,
    @SerializedName("admission_number") var admissionNumber: String? = null,
    @SerializedName("password") var password: String? = null
)