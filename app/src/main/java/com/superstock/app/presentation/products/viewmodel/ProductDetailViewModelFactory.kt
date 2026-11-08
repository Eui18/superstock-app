package com.superstock.app.presentation.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.superstock.app.data.repository.ProductRepository

class ProductDetailViewModelFactory(
    private val productRepository: ProductRepository,
    private val productId: Int
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailViewModel::class.java)) {
            return ProductDetailViewModel(productRepository, productId) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}