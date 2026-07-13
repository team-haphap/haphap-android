package com.haphap.app.data.remote.datasource.api.register

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.register.RegisterNameListResponseDto
import com.haphap.app.data.remote.dto.register.RegisterStageListResponseDto

interface RegisterDataSource {
    suspend fun getPostingNames(): BaseResponse<RegisterNameListResponseDto>
    suspend fun getPostingStages(postingId: Int): BaseResponse<RegisterStageListResponseDto>
}