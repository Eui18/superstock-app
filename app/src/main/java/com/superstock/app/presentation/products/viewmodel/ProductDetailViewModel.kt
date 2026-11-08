package com.superstock.app.presentation.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superstock.app.data.repository.ProductNotFoundException
import com.superstock.app.data.repository.ProductRepository
import com.superstock.app.presentation.products.state.ProductDetailUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductDetailViewModel(
    private val productRepository: ProductRepository,
    private val productId: Int
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductDetailUiState())
    val uiState: StateFlow<ProductDetailUiState> = _uiState.asStateFlow()

    init {
        loadProduct()
    }

    fun loadProduct() {
        if (_uiState.value.isLoading) {
            return
        }

        _uiState.update { it.copy(isLoading = true, errorMessage = null) }
        viewModelScope.launch {
            productRepository.getProductById(productId).fold(
                onSuccess = { product ->
                    _uiState.update { current ->
                        current.copy(isLoading = false, product = product)
                    }
                },
                onFailure = { error ->
                    _uiState.update { current ->
                        current.copy(
                            isLoading = false,
                            errorMessage = error.message ?: "No se pudo cargar el producto"
                        )
                    }
                }
            )
        }
    }

    fun requestDelete() {
        _uiState.update { current ->
            if (current.product == null || current.isLoading || current.isDeleting) {
                current
            } else {
                current.copy(showDeleteDialog = true)
            }
        }
    }

    fun dismissDelete() {
        _uiState.update { it.copy(showDeleteDialog = false) }
    }

    fun confirmDelete() {
        val state = _uiState.value
        if (!state.showDeleteDialog || state.isDeleting) {
            return
        }

        _uiState.update { it.copy(showDeleteDialog = false, isDeleting = true) }
        viewModelScope.launch {
            productRepository.deleteProduct(productId).fold(
                onSuccess = {
                    _uiState.update { current -> current.copy(isDeleted = true) }
                },
                onFailure = { error ->
                    if (error is ProductNotFoundException) {
                        _uiState.update { current ->
                            current.copy(isDeleted = true, alreadyDeleted = true)
                        }
                    } else {
                        _uiState.update { current ->
                            current.copy(
                                isDeleting = false,
                                userMessage = error.message ?: "No se pudo eliminar el producto"
                            )
                        }
                    }
                }
            )
        }
    }

    fun clearUserMessage() {
        _uiState.update { it.copy(userMessage = null) }
    }
}