package com.superstock.app.data.datasource.local

import kotlinx.coroutines.flow.Flow

interface SessionStorage {
    val token: Flow<String?>
    suspend fun saveToken(token: String)
    suspend fun clearToken()
}