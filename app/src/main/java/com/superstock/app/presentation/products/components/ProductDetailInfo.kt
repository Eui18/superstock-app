package com.superstock.app.presentation.products.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.superstock.app.R
import com.superstock.app.data.model.Product

@Composable
fun ProductDetailInfo(
    product: Product,
    modifier: Modifier = Modifier
) {
    val description = if (product.description.isBlank()) {
        stringResource(R.string.product_description_empty)
    } else {
        product.description
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = product.name,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = description,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = stringResource(
                R.string.product_category,
                stringResource(product.category.labelRes)
            )
        )
        Text(text = stringResource(R.string.product_price, product.price))
        Text(
            text = stringResource(
                R.string.product_stock_with_unit,
                product.stock,
                stringResource(product.unit.labelRes)
            )
        )
        Text(
            text = stringResource(
                if (product.active) R.string.product_active else R.string.product_inactive
            )
        )
    }
}