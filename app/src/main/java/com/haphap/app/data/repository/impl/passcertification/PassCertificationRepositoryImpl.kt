package com.haphap.app.data.repository.impl.passcertification

import android.net.Uri
import com.haphap.app.core.image.ImageMultipartConverter
import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.mapper.passcertification.toDetailModel
import com.haphap.app.data.mapper.passcertification.toRejectedModels
import com.haphap.app.data.model.passcertification.PassCertificationDetailModel
import com.haphap.app.data.model.passcertification.PassCertificationModel
import com.haphap.app.data.remote.datasource.api.passcertification.PassCertificationDataSource
import com.haphap.app.data.remote.dto.checkData
import com.haphap.app.data.remote.dto.passcertification.VerificationResubmitRequestDto
import com.haphap.app.data.repository.api.passcertification.PassCertificationRepository
import javax.inject.Inject

class PassCertificationRepositoryImpl @Inject constructor(
    private val passCertificationDataSource: PassCertificationDataSource,
    private val imageMultipartConverter: ImageMultipartConverter,
) : PassCertificationRepository {
    override suspend fun getRejectedCertifications(): Result<List<PassCertificationModel>> =
        suspendRunCatching {
            passCertificationDataSource.getVerificationSummary()
                .checkData()
                .toRejectedModels()
        }

    // TODO: 상세 조회 API가 생기면 교체. 지금은 현황 조회 응답에서 해당 건을 찾아 사용
    override suspend fun getCertificationDetail(registrationId: Long): Result<PassCertificationDetailModel> =
        suspendRunCatching {
            passCertificationDataSource.getVerificationSummary()
                .checkData()
                .items
                .first { it.registrationId == registrationId }
                .toDetailModel()
        }

    override suspend fun postVerificationImage(uri: Uri): Result<Long> =
        suspendRunCatching {
            val part = imageMultipartConverter.toPart(uri, VERIFICATION_IMAGE_PART_NAME)
            passCertificationDataSource.postVerificationImage(part).checkData().imageIds.firstOrNull()
                ?: throw IllegalStateException("Uploaded image id is missing")
        }

    override suspend fun putVerificationImages(registrationId: Long, imageIds: List<Long>): Result<Unit> =
        suspendRunCatching {
            passCertificationDataSource.putVerificationImages(
                registrationId = registrationId,
                request = VerificationResubmitRequestDto(verificationImageIds = imageIds),
            ).checkData()
        }

    companion object {
        private const val VERIFICATION_IMAGE_PART_NAME = "files"
    }
}
