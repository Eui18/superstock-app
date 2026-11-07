package com.superstock.app.data.datasource.remote

import com.superstock.app.data.datasource.local.SessionStorage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class AuthInterceptor(
    private val sessionStorage: SessionStorage
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (request.isAuthRequest()) {
            return chain.proceed(request)
        }

        val token = runBlocking { sessionStorage.token.first() }
        val authorizedRequest = if (token.isNullOrBlank()) {
            request
        } else {
            request.newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        }

        val response = chain.proceed(authorizedRequest)

        if (response.code == 401) {
            runBlocking { sessionStorage.clearToken() }
        }

        return response
    }

    private fun Request.isAuthRequest(): Boolean {
        val path = url.encodedPath
        return path.endsWith("/login") || path.endsWith("/register")
    }
}