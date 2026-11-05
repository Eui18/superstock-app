package com.superstock.app.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(
    val message: String
)