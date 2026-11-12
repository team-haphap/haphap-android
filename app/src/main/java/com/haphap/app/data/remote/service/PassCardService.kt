package com.haphap.app.data.remote.service

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.passcard.PassCardDetailResponseDto
import com.haphap.app.data.remote.dto.passcard.PassCardStorageResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface PassCardService {
    @GET("api/v1/pass-cards")
    suspend fun getPassCardStorage(): BaseResponse<PassCardStorageResponseDto>

    @GET("api/v1/pass-cards/{registrationId}")
    suspend fun getPassCardDetail(
        @Path("registrationId") registrationId: Int,
    ): BaseResponse<PassCardDetailResponseDto>
}