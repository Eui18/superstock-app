package com.superstock.app.presentation.products.state

data class ProductFormUiState(
    val isEditing: Boolean = false,
    val name: String = "",
    val description: String = "",
    val price: String = "",
    val stock: String = "",
    val category: String = "",
    val active: Boolean = true,
    val nameError: Boolean = false,
    val priceError: Boolean = false,
    val stockError: Boolean = false,
    val categoryError: Boolean = false,
    val isLoadingProduct: Boolean = false,
    val loadErrorMessage: String? = null,
    val isSaving: Boolean = false,
    val isSaved: Boolean = false,
    val userMessage: String? = null
)