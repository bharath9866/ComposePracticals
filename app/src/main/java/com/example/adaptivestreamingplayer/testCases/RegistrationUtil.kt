package com.example.adaptivestreamingplayer.testCases

object RegistrationUtil {
    private val existingUser: ArrayList<String> = arrayListOf("Paruchuri", "Kumar")

    /**
     * the input is not valid if...
     * ...the username and password is not empty
     * ...the the username is already exist
     * ...the password and confirm password is not same
     * ... the password not contains less than 3
     */

    fun validateRegistrationInput(
        userName: String,
        password: String,
        confirmPassword: String
    ): Boolean {
        if (userName.isEmpty()) return false
        if (userName in existingUser) return false
        if (password != confirmPassword) return false
        if (password.count() <= 2) return false
        return true
    }
}