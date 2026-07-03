package com.haphap.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ErrorResponseDto(
    val status: Int,
    val code: String,
    val message: String,
)