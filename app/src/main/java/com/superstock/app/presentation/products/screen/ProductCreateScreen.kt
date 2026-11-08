package com.superstock.app.presentation.products.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superstock.app.SuperStockApplication
import com.superstock.app.presentation.products.viewmodel.ProductCreateViewModel

@Composable
fun ProductCreateScreen(
    onBackClick: () -> Unit,
    onProductCreated: () -> Unit,
    productCreateViewModel: ProductCreateViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as SuperStockApplication)
            .container.productCreateViewModelFactory
    )
) {
    val uiState by productCreateViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isCreated) {
        if (uiState.isCreated) {
            onProductCreated()
        }
    }

    ProductCreateContent(
        uiState = uiState,
        onNameChange = productCreateViewModel::onNameChange,
        onDescriptionChange = productCreateViewModel::onDescriptionChange,
        onPriceChange = productCreateViewModel::onPriceChange,
        onStockChange = productCreateViewModel::onStockChange,
        onCategoryChange = productCreateViewModel::onCategoryChange,
        onActiveChange = productCreateViewModel::onActiveChange,
        onSaveClick = productCreateViewModel::createProduct,
        onBackClick = onBackClick,
        onMessageShown = productCreateViewModel::clearUserMessage
    )
}