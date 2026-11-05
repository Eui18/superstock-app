package com.superstock.app.presentation.auth.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superstock.app.data.repository.AuthRepository
import com.superstock.app.presentation.auth.state.LoginUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val MIN_PASSWORD_LENGTH = 8

class LoginViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = false) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = false) }
    }

    fun login() {
        val state = _uiState.value
        if (state.isLoading) {
            return
        }

        val email = state.email.trim()
        val emailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
        val passwordValid = state.password.length >= MIN_PASSWORD_LENGTH

        _uiState.update { it.copy(emailError = !emailValid, passwordError = !passwordValid) }
        if (!emailValid || !passwordValid) {
            return
        }

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            authRepository.login(email, state.password).fold(
                onSuccess = {
                    _uiState.update { current ->
                        current.copy(isLoading = false, userMessage = "Inicio de sesión correcto")
                    }
                },
                onFailure = { error ->
                    _uiState.update { current ->
                        current.copy(
                            isLoading = false,
                            userMessage = error.message ?: "No se pudo iniciar sesión"
                        )
                    }
                }
            )
        }
    }

    fun clearUserMessage() {
        _uiState.update { it.copy(userMessage = null) }
    }
}