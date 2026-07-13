package com.haphap.app.data.remote.service.register

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.register.RegisterNameListResponseDto
import com.haphap.app.data.remote.dto.register.RegisterRequestDto
import com.haphap.app.data.remote.dto.register.RegisterStageListResponseDto
import com.haphap.app.data.remote.dto.register.RegistrationResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface RegisterService {
    @GET("api/v1/postings/name")
    suspend fun getPostingNames(): BaseResponse<RegisterNameListResponseDto>

    @GET("api/v1/postings/{postingId}/stages")
    suspend fun getPostingStages(
        @Path("postingId") postingId: Int,
    ): BaseResponse<RegisterStageListResponseDto>

    @POST("api/v1/registrations")
    suspend fun postRegistration(
        @Body request: RegisterRequestDto,
    ): BaseResponse<RegistrationResponseDto>

    @GET("api/v1/registrations/{postingId}/{stageId}")
    suspend fun getRegistrationCheck(
        @Path("postingId") postingId: Int,
        @Path("stageId") stageId: Int,
    ): BaseResponse<Unit>
}