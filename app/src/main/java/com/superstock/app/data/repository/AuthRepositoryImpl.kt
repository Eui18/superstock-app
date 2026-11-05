package com.superstock.app.data.repository

import com.superstock.app.data.datasource.local.SessionStorage
import com.superstock.app.data.datasource.remote.AuthApiService
import com.superstock.app.data.datasource.remote.safeApiCall
import com.superstock.app.data.dto.LoginRequestDto

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val sessionStorage: SessionStorage
) : AuthRepository {

    override suspend fun login(email: String, password: String): Result<Unit> {
        return safeApiCall {
            val response = authApiService.login(LoginRequestDto(email, password))
            sessionStorage.saveToken(response.token)
        }
    }
}