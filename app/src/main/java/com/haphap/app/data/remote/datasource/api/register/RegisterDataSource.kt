package com.haphap.app.data.remote.datasource.api.register

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.register.RegisterNameListResponseDto
import com.haphap.app.data.remote.dto.register.RegisterRequestDto
import com.haphap.app.data.remote.dto.register.RegisterStageListResponseDto
import com.haphap.app.data.remote.dto.register.RegistrationResponseDto

interface RegisterDataSource {
    suspend fun getPostingNames(): BaseResponse<RegisterNameListResponseDto>
    suspend fun getPostingStages(postingId: Int): BaseResponse<RegisterStageListResponseDto>
    suspend fun postRegistration(request: RegisterRequestDto): BaseResponse<RegistrationResponseDto>
    suspend fun getRegistrationCheck(postingId: Int, stageId: Int): BaseResponse<Unit>
}