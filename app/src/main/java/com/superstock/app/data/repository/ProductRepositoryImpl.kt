package com.superstock.app.data.repository

import com.superstock.app.data.datasource.remote.ApiException
import com.superstock.app.data.datasource.remote.ProductApiService
import com.superstock.app.data.datasource.remote.safeApiCall
import com.superstock.app.data.mapper.toModel
import com.superstock.app.data.mapper.toModelList
import com.superstock.app.data.mapper.toRequestDto
import com.superstock.app.data.model.Product
import com.superstock.app.data.model.ProductInput

class ProductRepositoryImpl(
    private val productApiService: ProductApiService
) : ProductRepository {

    override suspend fun getProducts(): Result<List<Product>> {
        return safeApiCall { productApiService.getProducts().toModelList() }
    }

    override suspend fun getProductById(id: Int): Result<Product> {
        return safeApiCall { productApiService.getProductById(id).toModel() }
    }

    override suspend fun createProduct(input: ProductInput): Result<Product> {
        return safeApiCall { productApiService.createProduct(input.toRequestDto()).toModel() }
    }

    override suspend fun updateProduct(id: Int, input: ProductInput): Result<Product> {
        return safeApiCall { productApiService.updateProduct(id, input.toRequestDto()).toModel() }
    }

    override suspend fun deleteProduct(id: Int): Result<Unit> {
        val result = safeApiCall { productApiService.deleteProduct(id) }

        val error = result.exceptionOrNull()
        if (error is ApiException.Http && error.code == 404) {
            return Result.failure(
                ProductNotFoundException(error.message ?: "Producto no encontrado", error)
            )
        }
        return result
    }
}