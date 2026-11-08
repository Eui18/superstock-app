package com.superstock.app.data.datasource.remote

import com.superstock.app.data.dto.ProductDto
import com.superstock.app.data.dto.ProductRequestDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProductApiService {

    @GET("products")
    suspend fun getProducts(): List<ProductDto>

    @GET("products/{id}")
    suspend fun getProductById(@Path("id") id: Int): ProductDto

    @POST("products")
    suspend fun createProduct(@Body request: ProductRequestDto): ProductDto
}