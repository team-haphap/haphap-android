package com.haphap.app.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

private const val HTTP_OK = 200
private const val HTTP_CREATED = 201
private const val HTTP_DATA_NULL = 204

@Serializable
data class BaseResponse<T>(
    @SerialName("status")
    val status: Int,
    @SerialName("code")
    val code: String,
    @SerialName("message")
    val message: String,
    @SerialName("data")
    val data: T? = null,
)

fun <T> BaseResponse<T>.requireData(): T {
    if (status != HTTP_OK && status != HTTP_CREATED && status != HTTP_DATA_NULL) throw IllegalStateException("API request failed.")
    return data ?: throw IllegalStateException("Successful response but data was null.")
}
