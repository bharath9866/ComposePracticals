package com.example.adaptivestreamingplayer.api

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
        logCurl(request)
        val response = chain.proceed(request)
        return response
    }

    private fun logCurl(request: Request) {
        val curlCmd = StringBuilder("curl -X ${request.method}")

        for (i in 0 until request.headers.size) {
            curlCmd.append(" -H \"${request.headers.name(i)}: ${request.headers.value(i)}\"")
        }

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
}