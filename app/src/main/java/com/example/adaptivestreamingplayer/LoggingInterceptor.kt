package com.example.adaptivestreamingplayer

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.Buffer
import timber.log.Timber
import java.io.IOException

class LoggingInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        // Log the request
        logRequest(request)

        // Log CURL command
        logCurl(request)

        val startTime = System.nanoTime()
        val response = chain.proceed(request)
        val endTime = System.nanoTime()

        // Log the response
        logResponse(response, endTime - startTime)

        return response
    }

    private fun logRequest(request: Request) {
        Timber.d("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
        Timber.tag("API_REQUEST").d("METHOD: ${request.method}")
        Timber.tag("API_REQUEST").d("URL: ${request.url}")

        // Log headers
        if (request.headers.size > 0) {
            Timber.tag("API_REQUEST").d("HEADERS:")
            for (i in 0 until request.headers.size) {
                Timber.tag("API_REQUEST").d("  ${request.headers.name(i)}: ${request.headers.value(i)}")
            }
        }

        // Log request body
        request.body?.let { body ->
            try {
                val buffer = Buffer()
                body.writeTo(buffer)
                Timber.tag("API_REQUEST").d("BODY: ${buffer.readUtf8()}")
            } catch (e: Exception) {
                Timber.tag("API_REQUEST").d("BODY: (binary or error reading body)")
            }
        }
    }

    private fun logCurl(request: Request) {
        val curlCmd = StringBuilder("curl -X ${request.method}")

        // Add headers
        for (i in 0 until request.headers.size) {
            curlCmd.append(" -H \"${request.headers.name(i)}: ${request.headers.value(i)}\"")
        }

        // Add body
        request.body?.let { body ->
            try {
                val buffer = Buffer()
                body.writeTo(buffer)
                curlCmd.append(" -d '${buffer.readUtf8()}'")
            } catch (e: Exception) {
                curlCmd.append(" -d '(binary data)'")
            }
        }

        curlCmd.append(" \"${request.url}\"")
        Timber.tag("API_CURL").d(curlCmd.toString())
    }

    private fun logResponse(response: Response, responseTime: Long) {
        Timber.tag("API_RESPONSE").d("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
        Timber.tag("API_RESPONSE").d("STATUS: ${response.code} ${response.message}")
        Timber.tag("API_RESPONSE").d("TIME: ${responseTime / 1_000_000}ms")
        Timber.tag("API_RESPONSE").d("URL: ${response.request.url}")

        // Log response headers
        if (response.headers.size > 0) {
            Timber.tag("API_RESPONSE").d("HEADERS:")
            for (i in 0 until response.headers.size) {
                Timber.tag("API_RESPONSE").d("  ${response.headers.name(i)}: ${response.headers.value(i)}")
            }
        }

        // Log response body
        try {
            val responseBody = response.peekBody(1024 * 1024) // 1MB limit
            Timber.tag("API_RESPONSE").d("BODY: ${responseBody.string()}")
        } catch (e: Exception) {
            Timber.tag("API_RESPONSE").d("BODY: (error reading response body)")
        }

        Timber.tag("API_RESPONSE").d("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━")
    }
}