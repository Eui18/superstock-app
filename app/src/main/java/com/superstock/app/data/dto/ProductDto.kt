package com.superstock.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductDto(
    val id: Int,
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val category: String,
    val active: Boolean
)