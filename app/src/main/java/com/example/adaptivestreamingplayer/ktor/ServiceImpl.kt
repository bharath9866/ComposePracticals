package com.example.adaptivestreamingplayer.ktor

import com.example.adaptivestreamingplayer.ktor.dto.LoginRequest
import com.example.adaptivestreamingplayer.ktor.dto.OtpResponse
import com.example.adaptivestreamingplayer.urlIssue.SubjectResponse
import com.example.adaptivestreamingplayer.urlIssue.VideoPlaylistResponse
import io.ktor.client.HttpClient
import io.ktor.client.features.ClientRequestException
import io.ktor.client.features.RedirectResponseException
import io.ktor.client.features.ServerResponseException
import io.ktor.client.request.get
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

    override suspend fun getPlaylist(
        examId: Int,
        subTenantId: Int,
        playlistTypeId: Int
    ): VideoPlaylistResponse? {
        return try {
            client.get<VideoPlaylistResponse>() {
                url(HttpRoutes.getPlaylistUrl(examId.toString(), subTenantId.toString(), playlistTypeId.toString()))
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
    override suspend fun getSubjects(
        examId: Int,
        subTenantId: Int,
        tenantId: Int,
        status: String
    ): SubjectResponse? {
        return try {
            client.get<SubjectResponse> {
                url(
                    HttpRoutes.getSubjects(
                        examId = examId.toString(),
                        subtenantId = subTenantId.toString(),
                        tenantId = tenantId.toString(),
                        status = status
                    )
                )
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