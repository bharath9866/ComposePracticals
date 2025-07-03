package com.example.adaptivestreamingplayer.api

import com.example.adaptivestreamingplayer.api.models.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    suspend fun getUsers(): Response<List<User>>
}