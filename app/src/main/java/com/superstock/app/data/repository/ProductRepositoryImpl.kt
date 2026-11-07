package com.superstock.app.data.repository

import com.superstock.app.data.datasource.remote.ProductApiService
import com.superstock.app.data.datasource.remote.safeApiCall
import com.superstock.app.data.mapper.toModel
import com.superstock.app.data.mapper.toModelList
import com.superstock.app.data.model.Product

class ProductRepositoryImpl(
    private val productApiService: ProductApiService
) : ProductRepository {

    override suspend fun getProducts(): Result<List<Product>> {
        return safeApiCall { productApiService.getProducts().toModelList() }
    }

    override suspend fun getProductById(id: Int): Result<Product> {
        return safeApiCall { productApiService.getProductById(id).toModel() }
    }
}