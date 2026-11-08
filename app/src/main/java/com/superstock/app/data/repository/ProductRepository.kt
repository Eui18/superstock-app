package com.superstock.app.data.repository

import com.superstock.app.data.model.Product
import com.superstock.app.data.model.ProductInput

interface ProductRepository {
    suspend fun getProducts(): Result<List<Product>>
    suspend fun getProductById(id: Int): Result<Product>
    suspend fun createProduct(input: ProductInput): Result<Product>
    suspend fun updateProduct(id: Int, input: ProductInput): Result<Product>
}