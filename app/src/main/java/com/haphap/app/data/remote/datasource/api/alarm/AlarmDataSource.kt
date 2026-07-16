package com.haphap.app.data.remote.datasource.api.alarm

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.alarm.AlarmDeviceRequestDto
import com.haphap.app.data.remote.dto.auth.KakaoLoginRequestDto
import com.haphap.app.data.remote.dto.auth.KakaoLoginResponseDto

interface AlarmDataSource {
    suspend fun postAlarmDevice(request: AlarmDeviceRequestDto): BaseResponse<Unit>
}
