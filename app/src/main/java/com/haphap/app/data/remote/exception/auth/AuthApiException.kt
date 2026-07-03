package com.haphap.app.data.remote.exception.auth

class AuthApiException(
    val code: String,
    override val message: String,
) : Exception(message)