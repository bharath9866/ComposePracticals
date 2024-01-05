package com.example.ktor.dto

data class LoginRequest(
    val uid: String,
    val password: String,
    val userTypeId: Int? = 5,
)