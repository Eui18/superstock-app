package com.superstock.app.presentation.products.components

import com.superstock.app.R
import com.superstock.app.data.model.ProductCategory
import com.superstock.app.data.model.ProductUnit

val ProductCategory.labelRes: Int
    get() = when (this) {
        ProductCategory.FRUITS_VEGETABLES -> R.string.category_fruits_vegetables
        ProductCategory.DAIRY -> R.string.category_dairy
        ProductCategory.BEVERAGES -> R.string.category_beverages
        ProductCategory.GROCERIES -> R.string.category_groceries
        ProductCategory.BAKERY -> R.string.category_bakery
        ProductCategory.MEAT_COLD_CUTS -> R.string.category_meat_cold_cuts
        ProductCategory.CLEANING -> R.string.category_cleaning
        ProductCategory.PERSONAL_CARE -> R.string.category_personal_care
        ProductCategory.FROZEN -> R.string.category_frozen
    }

val ProductUnit.labelRes: Int
    get() = when (this) {
        ProductUnit.BOX -> R.string.unit_box
        ProductUnit.BAG -> R.string.unit_bag
        ProductUnit.BOTTLE -> R.string.unit_bottle
        ProductUnit.PIECE -> R.string.unit_piece
        ProductUnit.PACKAGE -> R.string.unit_package
        ProductUnit.CAN -> R.string.unit_can
        ProductUnit.JAR -> R.string.unit_jar
        ProductUnit.ROLL -> R.string.unit_roll
    }