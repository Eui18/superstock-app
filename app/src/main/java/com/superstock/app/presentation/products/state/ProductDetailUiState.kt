package com.superstock.app.presentation.products.state

import com.superstock.app.data.model.Product

data class ProductDetailUiState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val errorMessage: String? = null,
    val showDeleteDialog: Boolean = false,
    val isDeleting: Boolean = false,
    val isDeleted: Boolean = false,
    val alreadyDeleted: Boolean = false,
    val userMessage: String? = null
)