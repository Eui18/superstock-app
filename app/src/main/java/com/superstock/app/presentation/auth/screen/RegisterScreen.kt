package com.superstock.app.presentation.auth.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superstock.app.SuperStockApplication
import com.superstock.app.presentation.auth.components.RegisterForm
import com.superstock.app.presentation.auth.state.RegisterUiState
import com.superstock.app.presentation.auth.viewmodel.RegisterViewModel

@Composable
fun RegisterScreen(
    onLoginClick: () -> Unit,
    registerViewModel: RegisterViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as SuperStockApplication)
            .container.registerViewModelFactory
    )
) {
    val uiState by registerViewModel.uiState.collectAsStateWithLifecycle()

    RegisterContent(
        uiState = uiState,
        onNameChange = registerViewModel::onNameChange,
        onEmailChange = registerViewModel::onEmailChange,
        onPasswordChange = registerViewModel::onPasswordChange,
        onConfirmPasswordChange = registerViewModel::onConfirmPasswordChange,
        onRegisterClick = registerViewModel::register,
        onLoginClick = onLoginClick,
        onMessageShown = registerViewModel::clearUserMessage
    )
}

@Composable
fun RegisterContent(
    uiState: RegisterUiState,
    onNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onConfirmPasswordChange: (String) -> Unit,
    onRegisterClick: () -> Unit,
    onLoginClick: () -> Unit,
    onMessageShown: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            onMessageShown()
        }
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp, vertical = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "SuperStock",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Crear cuenta",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Regístrate para administrar tu inventario",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(24.dp))

            RegisterForm(
                name = uiState.name,
                email = uiState.email,
                password = uiState.password,
                confirmPassword = uiState.confirmPassword,
                nameError = uiState.nameError,
                emailError = uiState.emailError,
                passwordError = uiState.passwordError,
                confirmPasswordError = uiState.confirmPasswordError,
                isLoading = uiState.isLoading,
                onNameChange = onNameChange,
                onEmailChange = onEmailChange,
                onPasswordChange = onPasswordChange,
                onConfirmPasswordChange = onConfirmPasswordChange,
                onRegisterClick = onRegisterClick
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row {
                Text(
                    text = "¿Ya tienes cuenta? ",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "Inicia sesión",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.clickable { onLoginClick() }
                )
            }
        }
    }
}