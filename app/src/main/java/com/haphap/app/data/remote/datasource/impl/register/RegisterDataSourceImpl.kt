package com.haphap.app.data.remote.datasource.impl.register

import com.haphap.app.data.remote.datasource.api.register.RegisterDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.register.RegisterNameListResponseDto
import com.haphap.app.data.remote.dto.register.RegisterRequestDto
import com.haphap.app.data.remote.dto.register.RegisterStageListResponseDto
import com.haphap.app.data.remote.dto.register.RegistrationResponseDto
import com.haphap.app.data.remote.service.register.RegisterService
import jakarta.inject.Inject

class RegisterDataSourceImpl @Inject constructor(
    private val postingService: RegisterService
): RegisterDataSource {
    override suspend fun getPostingNames(): BaseResponse<RegisterNameListResponseDto> {
        return postingService.getPostingNames()
    }

    override suspend fun getPostingStages(postingId: Int): BaseResponse<RegisterStageListResponseDto> {
        return postingService.getPostingStages(postingId)
    }

    override suspend fun postRegistration(request: RegisterRequestDto): BaseResponse<RegistrationResponseDto> {
        return postingService.postRegistration(request)
    }

    override suspend fun getRegistrationCheck(postingId: Int, stageId: Int): BaseResponse<Unit> {
        return postingService.getRegistrationCheck(postingId, stageId)
    }
}