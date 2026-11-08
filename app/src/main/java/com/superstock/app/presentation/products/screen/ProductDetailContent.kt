package com.superstock.app.presentation.products.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.superstock.app.presentation.products.components.ProductDeleteDialog
import com.superstock.app.presentation.products.components.ProductDetailInfo
import com.superstock.app.presentation.products.state.ProductDetailUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailContent(
    uiState: ProductDetailUiState,
    onRetry: () -> Unit,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit,
    onDeleteConfirm: () -> Unit,
    onDeleteDismiss: () -> Unit,
    onBackClick: () -> Unit,
    onMessageShown: () -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val showActions = uiState.product != null && !uiState.isLoading && uiState.errorMessage == null

    LaunchedEffect(uiState.userMessage) {
        uiState.userMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            onMessageShown()
        }
    }
    BackHandler(enabled = uiState.isDeleting) { }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.product_detail_title)) },
                navigationIcon = {
                    TextButton(
                        onClick = onBackClick,
                        enabled = !uiState.isDeleting
                    ) {
                        Text(stringResource(R.string.product_detail_back))
                    }
                },
                actions = {
                    if (showActions) {
                        TextButton(
                            onClick = onEditClick,
                            enabled = !uiState.isDeleting
                        ) {
                            Text(stringResource(R.string.product_detail_edit))
                        }
                        TextButton(
                            onClick = onDeleteClick,
                            enabled = !uiState.isDeleting
                        ) {
                            Text(stringResource(R.string.product_detail_delete))
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.isLoading -> {
                    CircularProgressIndicator()
                }

                uiState.errorMessage != null -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(24.dp)
                    ) {
                        Text(
                            text = uiState.errorMessage,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(onClick = onRetry) {
                            Text(stringResource(R.string.product_detail_retry))
                        }
                    }
                }

                else -> {
                    uiState.product?.let { product ->
                        ProductDetailInfo(
                            product = product,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }

            if (uiState.isDeleting) {
                CircularProgressIndicator()
            }
        }
    }

    if (uiState.showDeleteDialog) {
        uiState.product?.let { product ->
            ProductDeleteDialog(
                productName = product.name,
                onConfirm = onDeleteConfirm,
                onDismiss = onDeleteDismiss
            )
        }
    }
}