package com.superstock.app.data.datasource.remote

import com.superstock.app.data.dto.AuthResponseDto
import com.superstock.app.data.dto.LoginRequestDto
import com.superstock.app.data.dto.RegisterRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiService {

    @POST("login")
    suspend fun login(@Body request: LoginRequestDto): AuthResponseDto

    @POST("register")
    suspend fun register(@Body request: RegisterRequestDto): AuthResponseDto
}