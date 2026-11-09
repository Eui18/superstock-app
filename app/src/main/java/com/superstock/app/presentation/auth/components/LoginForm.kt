package com.superstock.app.presentation.auth.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@Composable
fun LoginForm(
    email: String,
    password: String,
    emailError: Boolean,
    passwordError: Boolean,
    isLoading: Boolean,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AuthTextField(
            label = "Correo electrónico",
            value = email,
            onValueChange = onEmailChange,
            placeholder = "Correo electrónico",
            leadingIcon = Icons.Outlined.Email,
            isError = emailError,
            errorText = "Ingresa un correo electrónico válido.",
            keyboardType = KeyboardType.Email
        )

        AuthTextField(
            label = "Contraseña",
            value = password,
            onValueChange = onPasswordChange,
            placeholder = "Contraseña",
            leadingIcon = Icons.Outlined.Lock,
            isError = passwordError,
            errorText = "La contraseña debe tener al menos 8 caracteres.",
            isPassword = true,
            keyboardType = KeyboardType.Password
        )

        Button(
            onClick = onLoginClick,
            enabled = !isLoading,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Iniciar sesión",
                        style = MaterialTheme.typography.labelLarge
                    )
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        }
    }
}