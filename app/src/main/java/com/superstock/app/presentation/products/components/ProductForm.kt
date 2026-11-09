package com.superstock.app.presentation.products.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.superstock.app.R
import com.superstock.app.data.model.ProductCategory
import com.superstock.app.data.model.ProductUnit

@Composable
fun ProductForm(
    name: String,
    description: String,
    price: String,
    stock: String,
    category: ProductCategory?,
    unit: ProductUnit,
    active: Boolean,
    nameError: Boolean,
    priceError: Boolean,
    stockError: Boolean,
    categoryError: Boolean,
    isLoading: Boolean,
    submitText: String,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onStockChange: (String) -> Unit,
    onCategoryChange: (ProductCategory) -> Unit,
    onUnitChange: (ProductUnit) -> Unit,
    onActiveChange: (Boolean) -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val categoryLabels = ProductCategory.entries.map { stringResource(it.labelRes) }
    val unitLabels = ProductUnit.entries.map { stringResource(it.labelRes) }

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        OutlinedTextField(
            value = name,
            onValueChange = onNameChange,
            label = { Text(stringResource(R.string.product_form_name)) },
            isError = nameError,
            supportingText = if (nameError) {
                { Text(stringResource(R.string.product_form_name_error)) }
            } else {
                null
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChange,
            label = { Text(stringResource(R.string.product_form_description)) },
            minLines = 3,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = price,
            onValueChange = onPriceChange,
            label = { Text(stringResource(R.string.product_form_price)) },
            isError = priceError,
            supportingText = if (priceError) {
                { Text(stringResource(R.string.product_form_price_error)) }
            } else {
                null
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = stock,
            onValueChange = onStockChange,
            label = { Text(stringResource(R.string.product_form_stock)) },
            isError = stockError,
            supportingText = if (stockError) {
                { Text(stringResource(R.string.product_form_stock_error)) }
            } else {
                null
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        OptionDropdown(
            label = stringResource(R.string.product_form_unit),
            options = unitLabels,
            selectedIndex = unit.ordinal,
            onOptionSelected = { index -> onUnitChange(ProductUnit.entries[index]) }
        )

        OptionDropdown(
            label = stringResource(R.string.product_form_category),
            options = categoryLabels,
            selectedIndex = category?.ordinal,
            onOptionSelected = { index -> onCategoryChange(ProductCategory.entries[index]) },
            isError = categoryError,
            errorText = stringResource(R.string.product_form_category_error)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(stringResource(R.string.product_form_active))
            Switch(
                checked = active,
                onCheckedChange = onActiveChange
            )
        }

        Button(
            onClick = onSubmitClick,
            enabled = !isLoading,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            } else {
                Text(submitText)
            }
        }
    }
}