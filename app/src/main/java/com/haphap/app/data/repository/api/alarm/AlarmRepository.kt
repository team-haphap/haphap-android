package com.haphap.app.data.repository.api.alarm

interface AlarmRepository {
    suspend fun postAlarmDevice(
        deviceId: String,
        fcmToken: String,
        deviceType: String,
    ): Result<Unit>
}
