package com.example.adaptivestreamingplayer.api

import com.example.adaptivestreamingplayer.api.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ApiRepository(
    private val apiService: ApiService
) {
    suspend fun getUsers(): Result<List<User>> {
        return try {
            withContext(Dispatchers.IO) {
                val response = apiService.getUsers()
                if (response.isSuccessful) {
                    Result.success(response.body() ?: emptyList())
                } else {
                    Result.failure(Exception("Error: ${response.code()} ${response.message()}"))
                }
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}