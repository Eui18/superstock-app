package com.superstock.app.presentation.products.screen

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.superstock.app.R
import com.superstock.app.presentation.products.components.ProductBackButton
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
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.product_detail_title),
                        style = MaterialTheme.typography.headlineSmall,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                },
                navigationIcon = {
                    ProductBackButton(
                        onClick = onBackClick,
                        contentDescription = stringResource(R.string.product_detail_back),
                        enabled = !uiState.isDeleting
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            if (showActions) {
                DetailActionBar(
                    isDeleting = uiState.isDeleting,
                    onEditClick = onEditClick,
                    onDeleteClick = onDeleteClick
                )
            }
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
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = onRetry,
                            shape = RoundedCornerShape(16.dp)
                        ) {
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

@Composable
private fun DetailActionBar(
    isDeleting: Boolean,
    onEditClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedButton(
            onClick = onDeleteClick,
            enabled = !isDeleting,
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MaterialTheme.colorScheme.error
            ),
            modifier = Modifier
                .weight(1f)
                .height(52.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Delete,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.product_detail_delete),
                style = MaterialTheme.typography.labelLarge
            )
        }

        Button(
            onClick = onEditClick,
            enabled = !isDeleting,
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .weight(1f)
                .height(52.dp)
        ) {
            Icon(
                imageVector = Icons.Outlined.Edit,
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(R.string.product_detail_edit),
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}