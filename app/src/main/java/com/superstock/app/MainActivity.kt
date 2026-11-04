package com.superstock.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.superstock.app.presentation.auth.screen.LoginScreen
import com.superstock.app.ui.theme.SuperStockTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SuperStockTheme {
                LoginScreen()
            }
        }
    }
}