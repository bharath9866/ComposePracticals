package com.example.adaptivestreamingplayer.ktor

import android.util.Log
import com.example.adaptivestreamingplayer.ktor.dto.LoginRequest
import com.example.adaptivestreamingplayer.ktor.dto.OtpResponse
import com.example.adaptivestreamingplayer.urlIssue.SubjectResponse
import com.example.adaptivestreamingplayer.urlIssue.VideoPlaylistResponse
import com.example.adaptivestreamingplayer.utils.SLSharedPreference.accessToken
import com.example.adaptivestreamingplayer.utils.SLSharedPreference.slSubTenantId
import com.example.adaptivestreamingplayer.utils.SLSharedPreference.userId
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.DefaultRequest
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import io.ktor.client.features.observer.ResponseObserver
import io.ktor.client.request.HttpRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.content.OutgoingContent
import io.ktor.http.contentType

interface Service {
    suspend fun createPost(loginRequest: LoginRequest): OtpResponse?
    suspend fun getPlaylist(examId:Int, subTenantId:Int, playlistTypeId:Int): VideoPlaylistResponse?
    suspend fun getSubjects(examId:Int, subTenantId: Int, tenantId:Int, status:String): SubjectResponse?

    companion object {
        fun create(): Service {
            return PostsServiceImpl(
                client = HttpClient(Android) {

                    install(JsonFeature) {
                        serializer = GsonSerializer {
                            setPrettyPrinting()
                            setLenient()
                            disableHtmlEscaping()
                        }
                        engine {
                            connectTimeout = TIME_OUT
                            socketTimeout = TIME_OUT
                        }
                    }

                    install(Logging) {
                        logger = object : Logger {
                            override fun log(message: String) {
                                Log.d(TAG_KTOR_LOGGER, message)
                            }
                        }
                        level = LogLevel.ALL
                    }


                    install(ResponseObserver) {
                        onResponse { httpResponse ->
                            Log.d(TAG_HTTP_STATUS_LOGGER, "${httpResponse.status.value}")
                        }
                    }

                    install(DefaultRequest) {
                        contentType(ContentType.Application.Json)
                        header("X-Tenant", "srichaitanya")
                        header("Authorization", "Bearer $accessToken")
                        header("subtenantId", "45")
                        header("tenantId", "2")
                        header("userId", "873322")
                        header("tenant", "infinitylearn")
                        header("xplatform", "android")
                        header("product-id", "200")
                        header("xproduct", "infinitylearn")
                    }
                }
            )
        }

        private const val TIME_OUT = 10_000
        private const val TAG_KTOR_LOGGER = "ktorClient:"
        private const val TAG_HTTP_STATUS_LOGGER = "http_status:"
    }
}

fun HttpRequest.getCurl(httpRequestBuilder: HttpRequestBuilder) {

    val request = this
    val method = request.method.value
    val url = request.url.toString()
    val headers = request.headers.entries().joinToString("\n") { "${it.key}: ${it.value}" }



    // Handle request body if applicable (e.g., for POST requests)
    val body = if (httpRequestBuilder.body is OutgoingContent.ByteArrayContent) {
        (httpRequestBuilder.body as OutgoingContent.ByteArrayContent).bytes()
    } else {
        null
    }

    val curlCommand = StringBuilder("curl -X $method $url")
    if (headers.isNotEmpty()) {
        curlCommand.append("\n$headers")
    }
    if (body != null) {
        curlCommand.append("\n--data-binary \"$body\"")
    }
    Log.d("ktorCurl", curlCommand.toString())

}