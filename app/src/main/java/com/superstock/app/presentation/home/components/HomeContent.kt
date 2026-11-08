package com.superstock.app.presentation.home.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.superstock.app.presentation.products.components.ProductListContent
import com.superstock.app.presentation.products.state.ProductListUiState

@Composable
fun HomeContent(
    uiState: ProductListUiState,
    onRetry: () -> Unit,
    onLogoutClick: () -> Unit
) {
    Scaffold(
        topBar = { HomeTopBar(onLogoutClick = onLogoutClick) }
    ) { padding ->
        ProductListContent(
            uiState = uiState,
            onRetry = onRetry,
            modifier = Modifier.padding(padding)
        )
    }
}