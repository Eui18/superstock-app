package com.superstock.app.data.repository

interface AuthRepository {
    suspend fun login(email: String, password: String): Result<Unit>
}