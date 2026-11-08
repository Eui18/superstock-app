package com.superstock.app.data.model

data class ProductInput(
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val category: String,
    val active: Boolean
)