package com.superstock.app.presentation.products.components

import androidx.compose.ui.graphics.Color
import com.superstock.app.data.model.ProductCategory
import com.superstock.app.ui.theme.AccentCoffee
import com.superstock.app.ui.theme.AccentCoffeeSoft
import com.superstock.app.ui.theme.AccentGreen
import com.superstock.app.ui.theme.AccentGreenSoft
import com.superstock.app.ui.theme.AccentHoney
import com.superstock.app.ui.theme.AccentHoneySoft
import com.superstock.app.ui.theme.AccentIce
import com.superstock.app.ui.theme.AccentIceSoft
import com.superstock.app.ui.theme.AccentOrange
import com.superstock.app.ui.theme.AccentOrangeSoft
import com.superstock.app.ui.theme.AccentPurple
import com.superstock.app.ui.theme.AccentPurpleSoft
import com.superstock.app.ui.theme.AccentRed
import com.superstock.app.ui.theme.AccentRedSoft
import com.superstock.app.ui.theme.AccentSky
import com.superstock.app.ui.theme.AccentSkySoft
import com.superstock.app.ui.theme.AccentTeal
import com.superstock.app.ui.theme.AccentTealSoft

val ProductCategory.accentColor: Color
    get() = when (this) {
        ProductCategory.FRUITS_VEGETABLES -> AccentGreen
        ProductCategory.DAIRY -> AccentHoney
        ProductCategory.BEVERAGES -> AccentSky
        ProductCategory.GROCERIES -> AccentOrange
        ProductCategory.BAKERY -> AccentCoffee
        ProductCategory.MEAT_COLD_CUTS -> AccentRed
        ProductCategory.CLEANING -> AccentPurple
        ProductCategory.PERSONAL_CARE -> AccentTeal
        ProductCategory.FROZEN -> AccentIce
    }

val ProductCategory.accentSoftColor: Color
    get() = when (this) {
        ProductCategory.FRUITS_VEGETABLES -> AccentGreenSoft
        ProductCategory.DAIRY -> AccentHoneySoft
        ProductCategory.BEVERAGES -> AccentSkySoft
        ProductCategory.GROCERIES -> AccentOrangeSoft
        ProductCategory.BAKERY -> AccentCoffeeSoft
        ProductCategory.MEAT_COLD_CUTS -> AccentRedSoft
        ProductCategory.CLEANING -> AccentPurpleSoft
        ProductCategory.PERSONAL_CARE -> AccentTealSoft
        ProductCategory.FROZEN -> AccentIceSoft
    }