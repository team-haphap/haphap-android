package com.haphap.app.data.remote.service.register

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.register.RegisterNameListResponseDto
import com.haphap.app.data.remote.dto.register.RegisterStageListResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface RegisterService {
    @GET("api/v1/postings/name")
    suspend fun getPostingNames(): BaseResponse<RegisterNameListResponseDto>

    @GET("api/v1/postings/{postingId}/stages")
    suspend fun getPostingStages(
        @Path("postingId") postingId: Int,
    ): BaseResponse<RegisterStageListResponseDto>
}