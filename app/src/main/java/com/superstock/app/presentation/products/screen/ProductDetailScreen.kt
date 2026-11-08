package com.superstock.app.presentation.products.screen

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superstock.app.R
import com.superstock.app.SuperStockApplication
import com.superstock.app.presentation.products.viewmodel.ProductDetailViewModel

@Composable
fun ProductDetailScreen(
    productId: Int,
    productsChanged: Boolean,
    onProductsChangedHandled: () -> Unit,
    onEditClick: () -> Unit,
    onProductDeleted: () -> Unit,
    onBackClick: () -> Unit,
    productDetailViewModel: ProductDetailViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as SuperStockApplication)
            .container.productDetailViewModelFactory(productId)
    )
) {
    val context = LocalContext.current
    val uiState by productDetailViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(productsChanged) {
        if (productsChanged) {
            productDetailViewModel.loadProduct()
            onProductsChangedHandled()
        }
    }

    LaunchedEffect(uiState.isDeleted) {
        if (uiState.isDeleted) {
            if (uiState.alreadyDeleted) {
                Toast.makeText(context, R.string.product_delete_already_gone, Toast.LENGTH_SHORT).show()
            }
            onProductDeleted()
        }
    }

    ProductDetailContent(
        uiState = uiState,
        onRetry = productDetailViewModel::loadProduct,
        onEditClick = onEditClick,
        onDeleteClick = productDetailViewModel::requestDelete,
        onDeleteConfirm = productDetailViewModel::confirmDelete,
        onDeleteDismiss = productDetailViewModel::dismissDelete,
        onBackClick = onBackClick,
        onMessageShown = productDetailViewModel::clearUserMessage
    )
}