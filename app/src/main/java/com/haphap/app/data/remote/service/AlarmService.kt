package com.haphap.app.data.remote.service

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.alarm.AlarmDeviceRequestDto
import retrofit2.http.Body
import retrofit2.http.POST

interface AlarmService {
    @POST("/api/v1/alrams/device")
    suspend fun postAlarmDevice(
        @Body request: AlarmDeviceRequestDto,
    ): BaseResponse<Unit>
}
