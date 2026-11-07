package com.superstock.app.data.mapper

import com.superstock.app.data.dto.ProductDto
import com.superstock.app.data.model.Product

fun ProductDto.toModel(): Product {
    return Product(
        id = id,
        name = name,
        description = description,
        price = price,
        stock = stock,
        category = category,
        active = active
    )
}

fun List<ProductDto>.toModelList(): List<Product> {
    return map { it.toModel() }
}