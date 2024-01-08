package com.example.adaptivestreamingplayer.ktor

import com.example.adaptivestreamingplayer.ktor.dto.LoginRequest
import com.example.adaptivestreamingplayer.ktor.dto.OtpResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.post
import io.ktor.client.request.url

class PostsServiceImpl(
    private val client: HttpClient
) : Service {

    override suspend fun createPost(loginRequest: LoginRequest): OtpResponse? {
        return try {
            client.post<OtpResponse> {
                url(HttpRoutes.POSTS)
                body = loginRequest
            }
        } catch(e: RedirectResponseException) {
            // 3xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch(e: ClientRequestException) {
            // 4xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch(e: ServerResponseException) {
            // 5xx - responses
            println("Error: ${e.response.status.description}")
            null
        } catch(e: Exception) {
            println("Error: ${e.message}")
            null
        }
    }
}