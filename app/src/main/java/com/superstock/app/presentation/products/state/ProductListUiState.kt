package com.superstock.app.presentation.products.state

import com.superstock.app.data.model.Product
import com.superstock.app.data.model.ProductCategory

data class ProductListUiState(
    val isLoading: Boolean = false,
    val products: List<Product> = emptyList(),
    val errorMessage: String? = null,
    val selectedCategory: ProductCategory? = null
) {
    val visibleProducts: List<Product>
        get() {
            val category = selectedCategory
            return if (category == null) {
                products
            } else {
                products.filter { it.category == category }
            }
        }
}