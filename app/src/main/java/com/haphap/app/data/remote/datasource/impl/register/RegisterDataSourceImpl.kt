package com.haphap.app.data.remote.datasource.impl.register

import com.haphap.app.data.remote.datasource.api.register.RegisterDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.register.RegisterNameListResponseDto
import com.haphap.app.data.remote.dto.register.RegisterStageListResponseDto
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
}