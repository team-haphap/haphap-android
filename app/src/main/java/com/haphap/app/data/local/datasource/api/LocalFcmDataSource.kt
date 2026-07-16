package com.haphap.app.data.local.datasource.api

interface LocalFcmDataSource {
    suspend fun getFcmToken(): String?
    suspend fun setFcmToken(fcmToken: String)
}
