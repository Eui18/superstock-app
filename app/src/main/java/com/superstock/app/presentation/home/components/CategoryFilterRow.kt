package com.superstock.app.presentation.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Apps
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.superstock.app.R
import com.superstock.app.data.model.ProductCategory
import com.superstock.app.presentation.products.components.icon
import com.superstock.app.presentation.products.components.labelRes

@Composable
fun CategoryFilterRow(
    selectedCategory: ProductCategory?,
    onCategoryClick: (ProductCategory?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        CategoryChip(
            text = stringResource(R.string.home_category_all),
            icon = Icons.Outlined.Apps,
            selected = selectedCategory == null,
            onClick = { onCategoryClick(null) }
        )

        for (category in ProductCategory.entries) {
            CategoryChip(
                text = stringResource(category.labelRes),
                icon = category.icon,
                selected = selectedCategory == category,
                onClick = { onCategoryClick(category) }
            )
        }
    }
}

@Composable
private fun CategoryChip(
    text: String,
    icon: ImageVector,
    selected: Boolean,
    onClick: () -> Unit
) {
    val shape = RoundedCornerShape(50)

    val backgroundColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surface
    }
    val contentColor = if (selected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }
    val borderColor = if (selected) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.outlineVariant
    }
    val iconCircleColor = if (selected) {
        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
    } else {
        MaterialTheme.colorScheme.primary.copy(alpha = 0.12f)
    }
    val iconColor = if (selected) {
        MaterialTheme.colorScheme.onPrimary
    } else {
        MaterialTheme.colorScheme.primary
    }

    Row(
        modifier = Modifier
            .clip(shape)
            .background(backgroundColor)
            .border(1.dp, borderColor, shape)
            .clickable(onClick = onClick)
            .padding(start = 6.dp, end = 16.dp, top = 6.dp, bottom = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(28.dp)
                .clip(CircleShape)
                .background(iconCircleColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = iconColor,
                modifier = Modifier.size(16.dp)
            )
        }
        Text(
            text = text,
            style = MaterialTheme.typography.labelLarge,
            color = contentColor
        )
    }
}