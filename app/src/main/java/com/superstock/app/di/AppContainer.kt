package com.superstock.app.di

import android.content.Context
import com.superstock.app.BuildConfig
import com.superstock.app.data.datasource.local.DataStoreSessionStorage
import com.superstock.app.data.datasource.local.SessionStorage
import com.superstock.app.data.datasource.remote.AuthApiService
import com.superstock.app.data.datasource.remote.AuthInterceptor
import com.superstock.app.data.datasource.remote.apiJson
import com.superstock.app.data.repository.AuthRepository
import com.superstock.app.data.repository.AuthRepositoryImpl
import com.superstock.app.presentation.auth.viewmodel.LoginViewModelFactory
import com.superstock.app.presentation.auth.viewmodel.RegisterViewModelFactory
import com.superstock.app.presentation.home.viewmodel.HomeViewModelFactory
import com.superstock.app.presentation.session.viewmodel.SessionViewModelFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory

class AppContainer(
    private val context: Context
) {

    private val sessionStorage: SessionStorage by lazy {
        DataStoreSessionStorage(context)
    }

    private val authInterceptor: AuthInterceptor by lazy {
        AuthInterceptor(sessionStorage)
    }

    private val okHttpClient: OkHttpClient by lazy {
        val builder = OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
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

    private val authRepository: AuthRepository by lazy {
        AuthRepositoryImpl(authApiService, sessionStorage)
    }

    val sessionViewModelFactory: SessionViewModelFactory by lazy {
        SessionViewModelFactory(authRepository)
    }

    val loginViewModelFactory: LoginViewModelFactory by lazy {
        LoginViewModelFactory(authRepository)
    }

    val registerViewModelFactory: RegisterViewModelFactory by lazy {
        RegisterViewModelFactory(authRepository)
    }

    val homeViewModelFactory: HomeViewModelFactory by lazy {
        HomeViewModelFactory(authRepository)
    }
}