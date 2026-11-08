package com.superstock.app.presentation.products.validation

object ProductValidators {

    fun parsePrice(text: String): Double? {
        val price = text.trim().replace(',', '.').toDoubleOrNull() ?: return null
        return if (price.isFinite() && price > 0) price else null
    }

    fun parseStock(text: String): Int? {
        val stock = text.trim().toIntOrNull() ?: return null
        return if (stock >= 0) stock else null
    }
}