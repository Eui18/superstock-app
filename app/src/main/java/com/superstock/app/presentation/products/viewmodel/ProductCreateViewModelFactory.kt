package com.superstock.app.presentation.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.superstock.app.data.repository.ProductRepository

class ProductCreateViewModelFactory(
    private val productRepository: ProductRepository
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductCreateViewModel::class.java)) {
            return ProductCreateViewModel(productRepository) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida")
    }
}