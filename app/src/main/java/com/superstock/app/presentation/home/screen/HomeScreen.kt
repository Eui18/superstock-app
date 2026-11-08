package com.superstock.app.presentation.home.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.superstock.app.SuperStockApplication
import com.superstock.app.presentation.home.components.HomeContent
import com.superstock.app.presentation.home.viewmodel.HomeViewModel
import com.superstock.app.presentation.products.viewmodel.ProductListViewModel

@Composable
fun HomeScreen(
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

    HomeContent(
        uiState = uiState,
        onRetry = productListViewModel::loadProducts,
        onLogoutClick = homeViewModel::logout
    )
}