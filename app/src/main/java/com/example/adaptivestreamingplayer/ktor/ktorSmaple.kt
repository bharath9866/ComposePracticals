package com.example.adaptivestreamingplayer.ktor

import android.graphics.Movie
import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logging
import io.ktor.client.request.get
import io.ktor.http.URLBuilder
import io.ktor.http.takeFrom
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class KtorApiClient {

    private val httpClient = HttpClient {
        install(JsonFeature) {
            serializer = GsonSerializer {
                setPrettyPrinting()
                disableHtmlEscaping()
            }
        }
        install(Logging) {
            level = LogLevel.ALL
        }
    }

    suspend fun getMovies(): List<Movie> {
        val url = URLBuilder().apply {
            takeFrom("https://api.example.com/movies") // Replace with your API endpoint
        }
        return httpClient.get<List<Movie>>(url.build())
    }
}



@Serializable
data class User(val name: String, val yearOfBirth: Int)

fun main() {
// Faster encoding...
    val data = User("Louis", 1901)
    val string = Json.encodeToString(data)
    println(string) // {"name":"Louis","yearOfBirth":1901}

// ...and faster decoding!
    val obj = Json.decodeFromString<User>(string)
    println(obj) // User(name=Louis, yearOfBirth=1901)
}