package com.superstock.app.presentation.products.state

import com.superstock.app.data.model.Product

data class ProductListUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val errorMessage: String? = null
)