package com.haphap.app.data.remote.datasource.api.passcertification

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.passcertification.VerificationResubmitRequestDto
import com.haphap.app.data.remote.dto.passcertification.VerificationResubmitResponseDto
import com.haphap.app.data.remote.dto.passcertification.VerificationSummaryResponseDto
import com.haphap.app.data.remote.dto.register.VerificationImageUploadResponseDto
import okhttp3.MultipartBody

interface PassCertificationDataSource {
    suspend fun getVerificationSummary(): BaseResponse<VerificationSummaryResponseDto>

    suspend fun postVerificationImage(
        file: MultipartBody.Part,
    ): BaseResponse<VerificationImageUploadResponseDto>

    suspend fun putVerificationImages(
        registrationId: Long,
        request: VerificationResubmitRequestDto,
    ): BaseResponse<VerificationResubmitResponseDto>
}
