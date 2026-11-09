package com.superstock.app.presentation.home.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.superstock.app.R
import com.superstock.app.data.model.ProductCategory
import com.superstock.app.presentation.home.components.CategoryFilterRow
import com.superstock.app.presentation.home.components.HomeTopBar
import com.superstock.app.presentation.products.components.ProductListContent
import com.superstock.app.presentation.products.state.ProductListUiState

@Composable
fun HomeContent(
    uiState: ProductListUiState,
    onRetry: () -> Unit,
    onLogoutClick: () -> Unit,
    onProductClick: (Int) -> Unit,
    onAddProductClick: () -> Unit,
    onCategoryClick: (ProductCategory?) -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = { HomeTopBar(onLogoutClick = onLogoutClick) },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = onAddProductClick,
                shape = RoundedCornerShape(16.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                icon = {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                },
                text = {
                    Text(stringResource(R.string.home_add_product))
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            CategoryFilterRow(
                selectedCategory = uiState.selectedCategory,
                onCategoryClick = onCategoryClick
            )

            ProductListContent(
                uiState = uiState,
                onRetry = onRetry,
                onProductClick = onProductClick,
                modifier = Modifier.weight(1f)
            )
        }
    }
}