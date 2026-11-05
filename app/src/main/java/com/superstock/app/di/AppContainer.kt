package com.superstock.app.di

import android.content.Context
import com.superstock.app.BuildConfig
import com.superstock.app.data.datasource.local.DataStoreSessionStorage
import com.superstock.app.data.datasource.local.SessionStorage
import com.superstock.app.data.datasource.remote.AuthApiService
import com.superstock.app.data.datasource.remote.apiJson
import com.superstock.app.data.repository.AuthRepository
import com.superstock.app.data.repository.AuthRepositoryImpl
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class AppContainer(
    private val context: Context
) {

    private val okHttpClient: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC
            builder.addInterceptor(logging)
        }
        builder.build()
    }

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(apiJson.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    private val authApiService: AuthApiService by lazy {
        retrofit.create(AuthApiService::class.java)
    }

    private val sessionStorage: SessionStorage by lazy {
        DataStoreSessionStorage(context)
    }

    val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(authApiService, sessionStorage)
    }
}