package com.superstock.app.di

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.superstock.app.SuperStockApplication
import com.superstock.app.presentation.auth.viewmodel.LoginViewModel
import com.superstock.app.presentation.auth.viewmodel.RegisterViewModel

object AppViewModelProvider {

    val Factory = viewModelFactory {
        initializer {
            LoginViewModel(superStockApplication().container.authRepository)
        }

        initializer {
            RegisterViewModel(superStockApplication().container.authRepository)
        }
    }
}

private fun CreationExtras.superStockApplication(): SuperStockApplication {
    return this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as SuperStockApplication
}