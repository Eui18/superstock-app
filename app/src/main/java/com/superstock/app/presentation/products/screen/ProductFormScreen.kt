package com.superstock.app.presentation.products.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superstock.app.SuperStockApplication
import com.superstock.app.presentation.products.viewmodel.ProductFormViewModel

@Composable
fun ProductFormScreen(
    productId: Int?,
    onBackClick: () -> Unit,
    onSaved: () -> Unit,
    productFormViewModel: ProductFormViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as SuperStockApplication)
            .container.productFormViewModelFactory(productId)
    )
) {
    val uiState by productFormViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) {
            onSaved()
        }
    }

    ProductFormContent(
        uiState = uiState,
        onNameChange = productFormViewModel::onNameChange,
        onDescriptionChange = productFormViewModel::onDescriptionChange,
        onPriceChange = productFormViewModel::onPriceChange,
        onStockChange = productFormViewModel::onStockChange,
        onCategoryChange = productFormViewModel::onCategoryChange,
        onActiveChange = productFormViewModel::onActiveChange,
        onSaveClick = productFormViewModel::saveProduct,
        onRetryLoad = productFormViewModel::loadProduct,
        onBackClick = onBackClick,
        onMessageShown = productFormViewModel::clearUserMessage
    )
}