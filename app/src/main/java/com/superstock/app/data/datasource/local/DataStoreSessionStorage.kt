package com.superstock.app.data.datasource.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.sessionDataStore: DataStore<Preferences> by preferencesDataStore(name = "session")

class DataStoreSessionStorage(
    private val context: Context
) : SessionStorage {

    private val tokenKey = stringPreferencesKey("auth_token")

    override val token: Flow<String?> = context.sessionDataStore.data.map { preferences ->
        preferences[tokenKey]
    }

    override suspend fun saveToken(token: String) {
        context.sessionDataStore.edit { preferences ->
            preferences[tokenKey] = token
        }
    }

    override suspend fun clearToken() {
        context.sessionDataStore.edit { preferences ->
            preferences.remove(tokenKey)
        }
    }
}