package com.superstock.app.presentation.products.state

data class ProductCreateUiState(
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
    val isLoading: Boolean = false,
    val isCreated: Boolean = false,
    val userMessage: String? = null
)