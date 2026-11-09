package com.superstock.app.presentation.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.superstock.app.data.model.ProductCategory
import com.superstock.app.data.model.ProductInput
import com.superstock.app.data.model.ProductUnit
import com.superstock.app.data.repository.ProductRepository
import com.superstock.app.presentation.products.state.ProductFormUiState
import com.superstock.app.presentation.products.validation.ProductValidators
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class ProductFormViewModel(
    private val productRepository: ProductRepository,
    private val productId: Int?
) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductFormUiState(isEditing = productId != null))
    val uiState: StateFlow<ProductFormUiState> = _uiState.asStateFlow()

    init {
        loadProduct()
    }

    fun loadProduct() {
        if (productId == null || _uiState.value.isLoadingProduct) {
            return
        }

        _uiState.update { it.copy(isLoadingProduct = true, loadErrorMessage = null) }
        viewModelScope.launch {
            productRepository.getProductById(productId).fold(
                onSuccess = { product ->
                    _uiState.update { current ->
                        current.copy(
                            name = product.name,
                            description = product.description,
                            price = "%.2f".format(Locale.US, product.price),
                            stock = product.stock.toString(),
                            category = product.category,
                            unit = product.unit,
                            active = product.active,
                            isLoadingProduct = false
                        )
                    }
                },
                onFailure = { error ->
                    _uiState.update { current ->
                        current.copy(
                            isLoadingProduct = false,
                            loadErrorMessage = error.message ?: "No se pudo cargar el producto"
                        )
                    }
                }
            )
        }
    }

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

    fun onCategoryChange(category: ProductCategory) {
        _uiState.update { it.copy(category = category, categoryError = false) }
    }

    fun onUnitChange(unit: ProductUnit) {
        _uiState.update { it.copy(unit = unit) }
    }

    fun onActiveChange(active: Boolean) {
        _uiState.update { it.copy(active = active) }
    }

    fun saveProduct() {
        val state = _uiState.value
        if (state.isSaving || state.isLoadingProduct) {
            return
        }

        val name = state.name.trim()
        val category = state.category
        val price = ProductValidators.parsePrice(state.price)
        val stock = ProductValidators.parseStock(state.stock)

        _uiState.update {
            it.copy(
                nameError = name.isEmpty(),
                priceError = price == null,
                stockError = stock == null,
                categoryError = category == null
            )
        }
        if (name.isEmpty() || category == null || price == null || stock == null) {
            return
        }

        val input = ProductInput(
            name = name,
            description = state.description.trim(),
            price = price,
            stock = stock,
            category = category,
            unit = state.unit,
            active = state.active
        )

        _uiState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            val result = if (productId == null) {
                productRepository.createProduct(input)
            } else {
                productRepository.updateProduct(productId, input)
            }

            result.fold(
                onSuccess = {
                    _uiState.update { current -> current.copy(isSaved = true) }
                },
                onFailure = { error ->
                    _uiState.update { current ->
                        current.copy(
                            isSaving = false,
                            userMessage = error.message ?: "No se pudo guardar el producto"
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