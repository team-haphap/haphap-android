package com.haphap.app.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class BaseResponse<T>(
    val status: Int,
    val code: String,
    val message: String,
    val data: T? = null,
)
