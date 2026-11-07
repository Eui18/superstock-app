package com.superstock.app.data.repository

import com.superstock.app.data.datasource.local.SessionStorage
import com.superstock.app.data.datasource.remote.AuthApiService
import com.superstock.app.data.datasource.remote.safeApiCall
import com.superstock.app.data.dto.AuthResponseDto
import com.superstock.app.data.dto.LoginRequestDto
import com.superstock.app.data.dto.RegisterRequestDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AuthRepositoryImpl(
    private val authApiService: AuthApiService,
    private val sessionStorage: SessionStorage
) : AuthRepository {

    override val isLoggedIn: Flow<Boolean> = sessionStorage.token.map { !it.isNullOrBlank() }

    override suspend fun login(email: String, password: String): Result<Unit> {
        return authenticate { authApiService.login(LoginRequestDto(email, password)) }
    }

    override suspend fun register(name: String, email: String, password: String): Result<Unit> {
        return authenticate { authApiService.register(RegisterRequestDto(name, email, password)) }
    }

    override suspend fun logout() {
        sessionStorage.clearToken()
    }

    private suspend fun authenticate(call: suspend () -> AuthResponseDto): Result<Unit> {
        return safeApiCall {
            val response = call()
            sessionStorage.saveToken(response.token)
        }
    }
}