package com.superstock.app.presentation.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superstock.app.data.model.ProductInput
import com.superstock.app.data.repository.ProductRepository
import com.superstock.app.presentation.products.state.ProductCreateUiState
import com.superstock.app.presentation.products.validation.ProductValidators
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductCreateViewModel(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductCreateUiState())
    val uiState: StateFlow<ProductCreateUiState> = _uiState.asStateFlow()

    fun onNameChange(name: String) {
        _uiState.update { it.copy(name = name, nameError = false) }
    }

    fun onDescriptionChange(description: String) {
        _uiState.update { it.copy(description = description) }
    }

    fun onPriceChange(price: String) {
        _uiState.update { it.copy(price = price, priceError = false) }
    }

    fun onStockChange(stock: String) {
        _uiState.update { it.copy(stock = stock, stockError = false) }
    }

    fun onCategoryChange(category: String) {
        _uiState.update { it.copy(category = category, categoryError = false) }
    }

    fun onActiveChange(active: Boolean) {
        _uiState.update { it.copy(active = active) }
    }

    fun createProduct() {
        val state = _uiState.value
        if (state.isLoading) {
            return
        }

        val name = state.name.trim()
        val category = state.category.trim()
        val price = ProductValidators.parsePrice(state.price)
        val stock = ProductValidators.parseStock(state.stock)

        _uiState.update {
            it.copy(
                nameError = name.isEmpty(),
                priceError = price == null,
                stockError = stock == null,
                categoryError = category.isEmpty()
            )
        }
        if (name.isEmpty() || category.isEmpty() || price == null || stock == null) {
            return
        }

        val input = ProductInput(
            name = name,
            description = state.description.trim(),
            price = price,
            stock = stock,
            category = category,
            active = state.active
        )

        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            productRepository.createProduct(input).fold(
                onSuccess = {
                    // isLoading se queda en true: así el botón sigue bloqueado
                    // hasta que la pantalla se cierra y no se puede enviar dos veces.
                    _uiState.update { current -> current.copy(isCreated = true) }
                },
                onFailure = { error ->
                    _uiState.update { current ->
                        current.copy(
                            isLoading = false,
                            userMessage = error.message ?: "No se pudo crear el producto"
                        )
                    }
                }
            )
        }
    }

    fun clearUserMessage() {
        _uiState.update { it.copy(userMessage = null) }
    }
}