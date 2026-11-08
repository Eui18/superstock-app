package com.superstock.app.presentation.products.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.superstock.app.R
import com.superstock.app.presentation.products.components.ProductForm
import com.superstock.app.presentation.products.state.ProductCreateUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductCreateContent(
    uiState: ProductCreateUiState,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onStockChange: (String) -> Unit,
    onCategoryChange: (String) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    onMessageShown: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            onMessageShown()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.product_create_title)) },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text(stringResource(R.string.product_create_back))
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        ProductForm(
            name = uiState.name,
            description = uiState.description,
            price = uiState.price,
            stock = uiState.stock,
            category = uiState.category,
            active = uiState.active,
            nameError = uiState.nameError,
            priceError = uiState.priceError,
            stockError = uiState.stockError,
            categoryError = uiState.categoryError,
            isLoading = uiState.isLoading,
            submitText = stringResource(R.string.product_create_submit),
            onNameChange = onNameChange,
            onDescriptionChange = onDescriptionChange,
            onPriceChange = onPriceChange,
            onStockChange = onStockChange,
            onCategoryChange = onCategoryChange,
            onActiveChange = onActiveChange,
            onSubmitClick = onSaveClick,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        )
    }
}