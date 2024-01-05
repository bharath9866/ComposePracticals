package com.example.ktor

import com.example.ktor.dto.LoginRequest
import com.example.ktor.dto.OtpResponse
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.features.defaultRequest
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.Json
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.header

interface Service {
    suspend fun createPost(postRequest: LoginRequest): OtpResponse?

    companion object {
        fun create(): Service {
            return PostsServiceImpl(
                client = HttpClient(Android) {
                    install(Logging) {
                        level = LogLevel.ALL
                    }
                    install(JsonFeature) {
                        serializer = GsonSerializer() {
                            setPrettyPrinting()
                            disableHtmlEscaping()
                        }
                    }
                    defaultRequest {
                        header("X-Tenant", "srichaitanya")
                    }
                }
            )
        }
    }
}