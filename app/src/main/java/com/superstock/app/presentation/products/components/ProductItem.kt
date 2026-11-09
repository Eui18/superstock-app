package com.superstock.app.presentation.products.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.superstock.app.R
import com.superstock.app.data.model.Product

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductItem(
    product: Product,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val accent = product.category.accentColor
    val accentSoft = product.category.accentSoftColor

    val stockText = if (product.stock == 0) {
        stringResource(R.string.home_out_of_stock)
    } else {
        stringResource(R.string.home_stock, product.stock, stringResource(product.unit.labelRes))
    }
    val stockColor = if (product.stock == 0) {
        MaterialTheme.colorScheme.error
    } else {
        MaterialTheme.colorScheme.onSurfaceVariant
    }

    Card(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        modifier = modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(accentSoft),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = product.category.icon,
                    contentDescription = null,
                    tint = accent,
                    modifier = Modifier.size(26.dp)
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    SmallPill(
                        text = stringResource(product.category.labelRes),
                        color = accent
                    )
                    if (!product.active) {
                        SmallPill(
                            text = stringResource(R.string.product_status_inactive),
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = stringResource(R.string.home_price, product.price),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                SmallPill(text = stockText, color = stockColor)
            }
        }
    }
}

@Composable
private fun SmallPill(
    text: String,
    color: Color
) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyMedium,
        fontSize = 12.sp,
        color = color,
        maxLines = 1,
        modifier = Modifier
            .clip(RoundedCornerShape(50))
            .background(color.copy(alpha = 0.12f))
            .padding(horizontal = 10.dp, vertical = 4.dp)
    )
}