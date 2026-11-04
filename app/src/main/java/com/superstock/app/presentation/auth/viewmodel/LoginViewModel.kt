package com.superstock.app.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.superstock.app.presentation.auth.state.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update {
            it.copy(
                email = email,
                emailError = false,
                isFormValid = false
            )
        }
    }

    fun onPasswordChange(password: String) {
        _uiState.update {
            it.copy(
                password = password,
                passwordError = false,
                isFormValid = false
            )
        }
    }

    fun login() {
        val currentState = _uiState.value
        val validEmail = android.util.Patterns.EMAIL_ADDRESS
            .matcher(currentState.email.trim())
            .matches()

        val validPassword = currentState.password.length >= 8

        _uiState.update {
            it.copy(
                emailError = !validEmail,
                passwordError = !validPassword,
                isFormValid = validEmail && validPassword
            )
        }
    }
}