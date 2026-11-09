package com.superstock.app.data.mapper

import com.superstock.app.data.dto.ProductDto
import com.superstock.app.data.dto.ProductRequestDto
import com.superstock.app.data.model.Product
import com.superstock.app.data.model.ProductCategory
import com.superstock.app.data.model.ProductInput
import com.superstock.app.data.model.ProductUnit

fun ProductDto.toModel(): Product {
    return Product(
        id = id,
        name = name,
        description = description,
        price = price,
        stock = stock,
        category = ProductCategory.fromName(category) ?: ProductCategory.GROCERIES,
        unit = unit?.let { ProductUnit.fromName(it) } ?: ProductUnit.PIECE,
        active = active
    )
}

fun List<ProductDto>.toModelList(): List<Product> {
    return map { it.toModel() }
}

fun ProductInput.toRequestDto(): ProductRequestDto {
    return ProductRequestDto(
        name = name,
        description = description,
        price = price,
        stock = stock,
        category = category.name,
        unit = unit.name,
        active = active
    )
}