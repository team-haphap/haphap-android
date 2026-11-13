package com.haphap.app.data.remote.datasource.impl.passcertification

import com.haphap.app.data.remote.datasource.api.passcertification.PassCertificationDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.passcertification.VerificationResubmitRequestDto
import com.haphap.app.data.remote.dto.passcertification.VerificationResubmitResponseDto
import com.haphap.app.data.remote.dto.passcertification.VerificationSummaryResponseDto
import com.haphap.app.data.remote.dto.register.VerificationImageUploadResponseDto
import com.haphap.app.data.remote.service.PassCertificationService
import okhttp3.MultipartBody
import javax.inject.Inject

class PassCertificationDataSourceImpl @Inject constructor(
    private val passCertificationService: PassCertificationService,
) : PassCertificationDataSource {
    override suspend fun getVerificationSummary(): BaseResponse<VerificationSummaryResponseDto> =
        passCertificationService.getVerificationSummary()

    override suspend fun postVerificationImage(
        file: MultipartBody.Part,
    ): BaseResponse<VerificationImageUploadResponseDto> =
        passCertificationService.postVerificationImage(file)

    override suspend fun putVerificationImages(
        registrationId: Long,
        request: VerificationResubmitRequestDto,
    ): BaseResponse<VerificationResubmitResponseDto> =
        passCertificationService.putVerificationImages(registrationId, request)
}
