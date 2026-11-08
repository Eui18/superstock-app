package com.superstock.app.presentation.home.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.superstock.app.R
import com.superstock.app.presentation.home.components.HomeTopBar
import com.superstock.app.presentation.products.components.ProductListContent
import com.superstock.app.presentation.products.state.ProductListUiState

@Composable
fun HomeContent(
    uiState: ProductListUiState,
    onRetry: () -> Unit,
    onLogoutClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    onAddProductClick: () -> Unit
) {
    Scaffold(
        topBar = { HomeTopBar(onLogoutClick = onLogoutClick) },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = onAddProductClick) {
                Text(stringResource(R.string.home_add_product))
            }
        }
    ) { padding ->
        ProductListContent(
            uiState = uiState,
            onRetry = onRetry,
            onProductClick = onProductClick,
            modifier = Modifier.padding(padding)
        )
    }
}