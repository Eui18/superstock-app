package com.superstock.app.data.repository

class ProductNotFoundException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)