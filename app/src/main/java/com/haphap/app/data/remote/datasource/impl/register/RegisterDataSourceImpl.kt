package com.haphap.app.data.remote.datasource.impl.register

import com.haphap.app.data.remote.datasource.api.register.RegisterDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.register.RegisterNameListResponseDto
import com.haphap.app.data.remote.dto.register.RegisterRequestDto
import com.haphap.app.data.remote.dto.register.RegisterStageListResponseDto
import com.haphap.app.data.remote.dto.register.RegistrationCheckRequestDto
import com.haphap.app.data.remote.dto.register.RegistrationResponseDto
import com.haphap.app.data.remote.service.register.RegisterService
import jakarta.inject.Inject

class RegisterDataSourceImpl @Inject constructor(
    private val registerService: RegisterService
): RegisterDataSource {
    override suspend fun getRegisterPostNames(): BaseResponse<RegisterNameListResponseDto> {
        return registerService.getRegisterPostNames()
    }

    override suspend fun getRegisterPostStages(postingId: Int): BaseResponse<RegisterStageListResponseDto> {
        return registerService.getRegisterPostStages(postingId)
    }

    override suspend fun postRegister(request: RegisterRequestDto): BaseResponse<RegistrationResponseDto> {
        return registerService.postRegistration(request)
    }

    override suspend fun getRegisterCheck(
        postingId: Int,
        stageId: Int,
        request: RegistrationCheckRequestDto,
    ): BaseResponse<Unit> {
        return registerService.postRegistrationCheck(postingId, stageId, request)
    }
}