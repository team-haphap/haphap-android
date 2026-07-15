package com.haphap.app.data.repository.impl.alarm

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.remote.datasource.api.alarm.AlarmDataSource
import com.haphap.app.data.remote.dto.alarm.AlarmDeviceRequestDto
import com.haphap.app.data.repository.api.alarm.AlarmRepository
import jakarta.inject.Inject

class AlarmRepositoryImpl @Inject constructor(
    private val alarmDatasource: AlarmDataSource,
) : AlarmRepository {

    override suspend fun postAlarmDevice(
        deviceId: String,
        fcmToken: String,
        deviceType: String
    ): Result<Unit> =
        suspendRunCatching {
            alarmDatasource.postAlarmDevice(
                AlarmDeviceRequestDto(
                    deviceId = deviceId,
                    fcmToken = fcmToken,
                    deviceType = deviceType,
                )
            )
        }
}
