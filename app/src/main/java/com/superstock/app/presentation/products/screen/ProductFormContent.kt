package com.superstock.app.presentation.products.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.superstock.app.R
import com.superstock.app.data.model.ProductCategory
import com.superstock.app.data.model.ProductUnit
import com.superstock.app.presentation.products.components.ProductForm
import com.superstock.app.presentation.products.state.ProductFormUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductFormContent(
    uiState: ProductFormUiState,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onStockChange: (String) -> Unit,
    onCategoryChange: (ProductCategory) -> Unit,
    onUnitChange: (ProductUnit) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onSaveClick: () -> Unit,
    onRetryLoad: () -> Unit,
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

    val titleRes = if (uiState.isEditing) R.string.product_edit_title else R.string.product_create_title
    val submitRes = if (uiState.isEditing) R.string.product_edit_submit else R.string.product_create_submit

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(titleRes)) },
                navigationIcon = {
                    TextButton(onClick = onBackClick) {
                        Text(stringResource(R.string.product_create_back))
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        when {
            uiState.isLoadingProduct -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            uiState.loadErrorMessage != null -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            text = uiState.loadErrorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onRetryLoad) {
                            Text(stringResource(R.string.product_form_retry))
                        }
                    }
                }
            }

            else -> {
                ProductForm(
                    name = uiState.name,
                    description = uiState.description,
                    price = uiState.price,
                    stock = uiState.stock,
                    category = uiState.category,
                    unit = uiState.unit,
                    active = uiState.active,
                    nameError = uiState.nameError,
                    priceError = uiState.priceError,
                    stockError = uiState.stockError,
                    categoryError = uiState.categoryError,
                    isLoading = uiState.isSaving,
                    submitText = stringResource(submitRes),
                    onNameChange = onNameChange,
                    onDescriptionChange = onDescriptionChange,
                    onPriceChange = onPriceChange,
                    onStockChange = onStockChange,
                    onCategoryChange = onCategoryChange,
                    onUnitChange = onUnitChange,
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
    }
}