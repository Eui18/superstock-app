package com.superstock.app.presentation.products.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionDropdown(
    label: String,
    options: List<String>,
    selectedIndex: Int?,
    onOptionSelected: (Int) -> Unit,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    errorText: String? = null,
    leadingIcon: ImageVector? = null
) {
    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier
    ) {
        OutlinedTextField(
            value = selectedIndex?.let { options[it] }.orEmpty(),
            onValueChange = {},
            readOnly = true,
            label = { Text(label) },
            leadingIcon = if (leadingIcon != null) {
                { Icon(imageVector = leadingIcon, contentDescription = null) }
            } else {
                null
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            isError = isError,
            supportingText = if (isError && errorText != null) {
                { Text(errorText) }
            } else {
                null
            },
            shape = RoundedCornerShape(16.dp),
            colors = productFieldColors(),
            modifier = Modifier
                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEachIndexed { index, text ->
                DropdownMenuItem(
                    text = { Text(text) },
                    onClick = {
                        onOptionSelected(index)
                        expanded = false
                    }
                )
            }
        }
    }
}