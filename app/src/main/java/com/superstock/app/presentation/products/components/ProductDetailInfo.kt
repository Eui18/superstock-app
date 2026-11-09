package com.superstock.app.presentation.products.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AttachMoney
import androidx.compose.material.icons.outlined.Inventory2
import androidx.compose.material.icons.outlined.Straighten
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superstock.app.R
import com.superstock.app.data.model.Product

@Composable
fun ProductDetailInfo(
    product: Product,
    modifier: Modifier = Modifier
) {
    val accent = product.category.accentColor
    val accentSoft = product.category.accentSoftColor

    val description = if (product.description.isBlank()) {
        stringResource(R.string.product_description_empty)
    } else {
        product.description
    }

    val stockText = if (product.stock == 0) {
        stringResource(R.string.home_out_of_stock)
    } else {
        stringResource(R.string.home_stock, product.stock, stringResource(product.unit.labelRes))
    }
    val stockColor = if (product.stock == 0) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.onSurface
    }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DetailHeader(
            product = product,
            accent = accent,
            accentSoft = accentSoft
        )

        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard(
                icon = Icons.Outlined.AttachMoney,
                label = stringResource(R.string.product_form_price),
                value = stringResource(R.string.home_price, product.price),
                valueColor = MaterialTheme.colorScheme.onSurface,
                accent = accentSoft,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                icon = Icons.Outlined.Inventory2,
                label = stringResource(R.string.product_form_stock),
                value = stockText,
                valueColor = stockColor,
                accent = accentSoft,
                modifier = Modifier.weight(1f)
            )
        }

        DetailSection(title = stringResource(R.string.detail_description_title)) {
            Text(
                text = description,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        DetailSection(title = stringResource(R.string.detail_info_title)) {
            InfoRow(
                icon = Icons.Outlined.Straighten,
                label = stringResource(R.string.product_form_unit),
                value = stringResource(product.unit.labelRes),
                accentSoft = accentSoft,
                accent = accent
            )
        }
    }
}

@Composable
private fun DetailHeader(
    product: Product,
    accent: Color,
    accentSoft: Color
) {
    val statusText = if (product.active) {
        stringResource(R.string.product_status_active)
    } else {
        stringResource(R.string.product_status_inactive)
    }
    val statusColor = if (product.active) accent else MaterialTheme.colorScheme.error
    val statusSoft = if (product.active) accentSoft else MaterialTheme.colorScheme.error.copy(alpha = 0.12f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(24.dp))
            .background(accentSoft)
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .clip(CircleShape)
                .background(accent.copy(alpha = 0.15f)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = product.category.icon,
                contentDescription = null,
                tint = accent,
                modifier = Modifier.size(36.dp)
            )
        }

        Text(
            text = product.name,
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.onSurface,
            textAlign = TextAlign.Center
        )

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Pill(
                text = stringResource(product.category.labelRes),
                color = accent,
                background = accent.copy(alpha = 0.15f)
            )
            Pill(
                text = statusText,
                color = statusColor,
                background = statusSoft
            )
        }
    }
}

@Composable
private fun Pill(
    text: String,
    color: Color,
    background: Color
) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelLarge,
        color = color,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(background)
            .padding(horizontal = 12.dp, vertical = 6.dp)
    )
}

@Composable
private fun StatCard(
    icon: ImageVector,
    label: String,
    value: String,
    valueColor: Color,
    accent: Color,
    modifier: Modifier = Modifier
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconBox(icon = icon, background = accent, tint = MaterialTheme.colorScheme.primary)
            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = valueColor
            )
        }
    }
}

@Composable
private fun DetailSection(
    title: String,
    content: @Composable () -> Unit
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            content()
        }
    }
}

@Composable
private fun InfoRow(
    icon: ImageVector,
    label: String,
    value: String,
    accentSoft: Color,
    accent: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        IconBox(icon = icon, background = accentSoft, tint = accent)
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun IconBox(
    icon: ImageVector,
    background: Color,
    tint: Color
) {
    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(background),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = tint,
            modifier = Modifier.size(22.dp)
        )
    }
}