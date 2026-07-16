package com.haphap.app.data.repository.api.alarm

interface AlarmRepository {
    suspend fun updateFcmToken(newFcmToken: String): Result<Unit>
}
