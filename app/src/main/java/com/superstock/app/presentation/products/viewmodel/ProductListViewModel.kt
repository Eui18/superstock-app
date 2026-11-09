package com.superstock.app.presentation.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superstock.app.data.model.ProductCategory
import com.superstock.app.data.repository.ProductRepository
import com.superstock.app.presentation.products.state.ProductListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductListUiState())
    val uiState: StateFlow<ProductListUiState> = _uiState.asStateFlow()

    init {
        loadProducts()
    }

    fun onCategorySelected(category: ProductCategory?) {
        _uiState.update { it.copy(selectedCategory = category) }
    }

    fun loadProducts() {
        if (_uiState.value.isLoading) {
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            productRepository.getProducts().fold(
                onSuccess = { products ->
                    _uiState.update { current ->
                        current.copy(isLoading = false, products = products)
                    }
                },
                onFailure = { error ->
                    _uiState.update { current ->
                        current.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "No se pudieron cargar los productos"
                        )
                    }
                }
            )
        }
    }
}