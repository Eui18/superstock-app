package com.superstock.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ProductRequestDto(
    val name: String,
    val description: String,
    val price: Double,
    val stock: Int,
    val category: String,
    val unit: String,
    val active: Boolean
)