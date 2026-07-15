package com.haphap.app.data.remote.datasource.impl.alarm

import com.haphap.app.data.remote.datasource.api.alarm.AlarmDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.alarm.AlarmDeviceRequestDto
import com.haphap.app.data.remote.service.AlarmService
import javax.inject.Inject


class AlarmDataSourceImpl @Inject constructor(
    private val alarmService: AlarmService,
) : AlarmDataSource {

    override suspend fun postAlarmDevice(request: AlarmDeviceRequestDto): BaseResponse<Unit> {
        return alarmService.postAlarmDevice(request)

    }
}
