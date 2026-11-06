package com.superstock.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.superstock.app.presentation.auth.screen.LoginScreen
import com.superstock.app.ui.theme.SuperStockTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import com.superstock.app.presentation.auth.screen.RegisterScreen

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SuperStockTheme {
                var showRegister by rememberSaveable { mutableStateOf(false) }

                if (showRegister) {
                    RegisterScreen(onLoginClick = { showRegister = false })
                } else {
                    LoginScreen(onRegisterClick = { showRegister = true })
                }
            }
        }
    }
}