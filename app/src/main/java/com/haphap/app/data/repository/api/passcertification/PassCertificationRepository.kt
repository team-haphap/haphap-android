package com.haphap.app.data.repository.api.passcertification

import android.net.Uri
import com.haphap.app.data.model.passcertification.PassCertificationDetailModel
import com.haphap.app.data.model.passcertification.PassCertificationModel

interface PassCertificationRepository {
    suspend fun getRejectedCertifications(): Result<List<PassCertificationModel>>
    suspend fun getCertificationDetail(registrationId: Long): Result<PassCertificationDetailModel>
    suspend fun postVerificationImage(uri: Uri): Result<Long>
    suspend fun putVerificationImages(registrationId: Long, imageIds: List<Long>): Result<Unit>
}
