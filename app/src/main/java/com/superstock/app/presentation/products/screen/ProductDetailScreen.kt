package com.superstock.app.presentation.products.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superstock.app.SuperStockApplication
import com.superstock.app.presentation.products.viewmodel.ProductDetailViewModel

@Composable
fun ProductDetailScreen(
    productId: Int,
    onBackClick: () -> Unit,
    productDetailViewModel: ProductDetailViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as SuperStockApplication)
            .container.productDetailViewModelFactory(productId)
    )
) {
    val uiState by productDetailViewModel.uiState.collectAsStateWithLifecycle()

    ProductDetailContent(
        uiState = uiState,
        onRetry = productDetailViewModel::loadProduct,
        onBackClick = onBackClick
    )
}