package com.superstock.app.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = GreenPrimary,
    onPrimary = White,
    primaryContainer = GreenSoft,
    onPrimaryContainer = GreenPrimaryDark,
    background = MintBackground,
    onBackground = TextDark,
    surface = White,
    onSurface = TextDark,
    onSurfaceVariant = TextGray,
    outlineVariant = FieldBorder,
    error = AccentRed,
    onError = White
)

@Composable
fun SuperStockTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}