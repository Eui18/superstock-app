package com.superstock.app.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superstock.app.data.repository.AuthRepository
import com.superstock.app.presentation.auth.state.LoginUiState
import com.superstock.app.presentation.common.validation.InputValidators
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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
        val emailValid = InputValidators.isValidEmail(email)
        val passwordValid = InputValidators.isValidPassword(state.password)

        _uiState.update { it.copy(emailError = !emailValid, passwordError = !passwordValid) }
        if (!emailValid || !passwordValid) {
            return
        }

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            authRepository.login(email, state.password).fold(
                onSuccess = {
                    _uiState.update { current ->
                        current.copy(isLoading = false)
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