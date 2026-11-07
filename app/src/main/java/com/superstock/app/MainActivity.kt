package com.superstock.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.superstock.app.ui.theme.SuperStockTheme
import com.superstock.app.presentation.navigation.SuperStockApp

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SuperStockTheme {
                SuperStockApp()
            }
        }
    }
}