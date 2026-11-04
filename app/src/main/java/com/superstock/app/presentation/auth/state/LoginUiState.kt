package com.superstock.app.presentation.auth.state

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: Boolean = false,
    val passwordError: Boolean = false,
    val isFormValid: Boolean = false
)