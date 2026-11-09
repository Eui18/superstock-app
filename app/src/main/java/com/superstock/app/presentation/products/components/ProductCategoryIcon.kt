package com.superstock.app.presentation.products.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AcUnit
import androidx.compose.material.icons.outlined.BakeryDining
import androidx.compose.material.icons.outlined.CleaningServices
import androidx.compose.material.icons.outlined.Eco
import androidx.compose.material.icons.outlined.EmojiFoodBeverage
import androidx.compose.material.icons.outlined.LocalDrink
import androidx.compose.material.icons.outlined.Restaurant
import androidx.compose.material.icons.outlined.ShoppingBasket
import androidx.compose.material.icons.outlined.Spa
import androidx.compose.ui.graphics.vector.ImageVector
import com.superstock.app.data.model.ProductCategory

val ProductCategory.icon: ImageVector
    get() = when (this) {
        ProductCategory.FRUITS_VEGETABLES -> Icons.Outlined.Eco
        ProductCategory.DAIRY -> Icons.Outlined.EmojiFoodBeverage
        ProductCategory.BEVERAGES -> Icons.Outlined.LocalDrink
        ProductCategory.GROCERIES -> Icons.Outlined.ShoppingBasket
        ProductCategory.BAKERY -> Icons.Outlined.BakeryDining
        ProductCategory.MEAT_COLD_CUTS -> Icons.Outlined.Restaurant
        ProductCategory.CLEANING -> Icons.Outlined.CleaningServices
        ProductCategory.PERSONAL_CARE -> Icons.Outlined.Spa
        ProductCategory.FROZEN -> Icons.Outlined.AcUnit
    }