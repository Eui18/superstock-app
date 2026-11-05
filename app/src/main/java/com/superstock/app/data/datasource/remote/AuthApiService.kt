package com.superstock.app.data.datasource.remote

import com.superstock.app.data.dto.AuthResponseDto
import com.superstock.app.data.dto.LoginRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("login")
    suspend fun login(@Body request: LoginRequestDto): AuthResponseDto
}