package com.haphap.app.data.remote.service

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.passcertification.VerificationResubmitRequestDto
import com.haphap.app.data.remote.dto.passcertification.VerificationResubmitResponseDto
import com.haphap.app.data.remote.dto.passcertification.VerificationSummaryResponseDto
import com.haphap.app.data.remote.dto.register.VerificationImageUploadResponseDto
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path

interface PassCertificationService {
    @GET("api/v1/members/me/verifications/summary")
    suspend fun getVerificationSummary(): BaseResponse<VerificationSummaryResponseDto>

    @Multipart
    @POST("api/v1/registrations/verification-images")
    suspend fun postVerificationImage(
        @Part file: MultipartBody.Part,
    ): BaseResponse<VerificationImageUploadResponseDto>

    @PUT("api/v1/registrations/{registrationId}/verification-images")
    suspend fun putVerificationImages(
        @Path("registrationId") registrationId: Long,
        @Body request: VerificationResubmitRequestDto,
    ): BaseResponse<VerificationResubmitResponseDto>
}
