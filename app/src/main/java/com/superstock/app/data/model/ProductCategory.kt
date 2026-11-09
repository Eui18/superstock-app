package com.superstock.app.data.model

enum class ProductCategory {
    FRUITS_VEGETABLES,
    DAIRY,
    BEVERAGES,
    GROCERIES,
    BAKERY,
    MEAT_COLD_CUTS,
    CLEANING,
    PERSONAL_CARE,
    FROZEN;

    companion object {
        fun fromName(name: String): ProductCategory? {
            return entries.firstOrNull { it.name == name }
        }
    }
}