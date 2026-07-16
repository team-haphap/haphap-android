package com.haphap.app.data.repository.api.alarm

interface AlarmRepository {
    suspend fun registerDeviceId(): Result<Unit>
    suspend fun updateFcmToken(newFcmToken: String): Result<Unit>
}
