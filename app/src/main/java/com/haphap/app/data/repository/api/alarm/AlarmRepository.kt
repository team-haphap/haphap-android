package com.haphap.app.data.repository.api.alarm

import com.haphap.app.data.model.alarm.AlarmDeviceModel

interface AlarmRepository {
    suspend fun postAlarmDevice(
        deviceId: String,
        fcmToken: String,
        deviceType: String,
    ): Result<Unit>
}
