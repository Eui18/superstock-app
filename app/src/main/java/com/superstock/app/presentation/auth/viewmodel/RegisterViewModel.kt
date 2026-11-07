package com.superstock.app.presentation.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superstock.app.data.repository.AuthRepository
import com.superstock.app.presentation.auth.state.RegisterUiState
import com.superstock.app.presentation.common.validation.InputValidators
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, nameError = false) }
    }

    fun onEmailChange(email: String) {
        _uiState.update { it.copy(email = email, emailError = false) }
    }

    fun onPasswordChange(password: String) {
        _uiState.update { it.copy(password = password, passwordError = false) }
    }

    fun onConfirmPasswordChange(confirmPassword: String) {
        _uiState.update {
            it.copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = false
            )
        }
    }

    fun register() {
        val state = _uiState.value
        if (state.isLoading) {
            return
        }

        val name = state.name.trim()
        val email = state.email.trim()
        val nameError = name.isEmpty()
        val emailError = !InputValidators.isValidEmail(email)
        val passwordError = !InputValidators.isValidPassword(state.password)
        val confirmPasswordError = state.password != state.confirmPassword

        _uiState.update {
            it.copy(
                nameError = nameError,
                emailError = emailError,
                passwordError = passwordError,
                confirmPasswordError = confirmPasswordError
            )
        }
        if (nameError || emailError || passwordError || confirmPasswordError) {
            return
        }

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            authRepository.register(name, email, state.password).fold(
                onSuccess = {
                    _uiState.update {current -> current.copy(isLoading = false) }
                },
                onFailure = { error ->
                    _uiState.update { current ->
                        current.copy(
                            isLoading = false,
                            userMessage = error.message ?: "No se pudo crear la cuenta"
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