package com.superstock.app.presentation.home.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superstock.app.SuperStockApplication
import com.superstock.app.presentation.home.viewmodel.HomeViewModel
import com.superstock.app.presentation.products.viewmodel.ProductListViewModel

@Composable
fun HomeScreen(
    productsChanged: Boolean,
    onProductsChangedHandled: () -> Unit,
    onProductClick: (Int) -> Unit,
    onAddProductClick: () -> Unit,
    homeViewModel: HomeViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as SuperStockApplication)
            .container.homeViewModelFactory
    ),
    productListViewModel: ProductListViewModel = viewModel(
        factory = (LocalContext.current.applicationContext as SuperStockApplication)
            .container.productListViewModelFactory
    )
) {
    val uiState by productListViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(productsChanged) {
        if (productsChanged) {
            productListViewModel.loadProducts()
            onProductsChangedHandled()
        }
    }

    HomeContent(
        uiState = uiState,
        onRetry = productListViewModel::loadProducts,
        onLogoutClick = homeViewModel::logout,
        onProductClick = onProductClick,
        onAddProductClick = onAddProductClick,
        onCategoryClick = productListViewModel::onCategorySelected
    )
}