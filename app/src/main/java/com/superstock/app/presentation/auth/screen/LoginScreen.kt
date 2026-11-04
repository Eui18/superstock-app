package com.superstock.app.presentation.auth.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superstock.app.presentation.auth.components.LoginForm
import com.superstock.app.presentation.auth.viewmodel.LoginViewModel

@Composable
fun LoginScreen( loginViewModel: LoginViewModel = viewModel()) {

    val uiState by loginViewModel.uiState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "SuperStock",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Inicia sesión para administrar tu inventario",
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(32.dp))

        LoginForm(
            email = uiState.email,
            password = uiState.password,
            emailError = uiState.emailError,
            passwordError = uiState.passwordError,
            onEmailChange = loginViewModel::onEmailChange,
            onPasswordChange = loginViewModel::onPasswordChange,
            onLoginClick = loginViewModel::login
        )

        if (uiState.isFormValid) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Datos válidos. ¡Formulario listo!",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}