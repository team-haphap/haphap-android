package com.haphap.app.data.mapper.passcertification

import com.haphap.app.core.extensions.toTimeFormat
import com.haphap.app.data.model.passcertification.PassCertificationDetailModel
import com.haphap.app.data.model.passcertification.PassCertificationModel
import com.haphap.app.data.remote.dto.passcertification.VerificationItemResponseDto
import com.haphap.app.data.remote.dto.passcertification.VerificationSummaryResponseDto

fun VerificationSummaryResponseDto.toRejectedModels(): List<PassCertificationModel> =
    items
        .filter { it.resubmittable }
        .sortedBy { it.resolvedAt }
        .map { it.toModel() }

fun VerificationItemResponseDto.toDetailModel(): PassCertificationDetailModel =
    PassCertificationDetailModel(
        companyName = companyName,
        title = postingTitle,
        stageName = stageName,
        submittedDate = submittedAt?.toTimeFormat(pattern = "yyyy.MM.dd.").orEmpty(),
    )

fun VerificationItemResponseDto.toModel(): PassCertificationModel =
    PassCertificationModel(
        id = registrationId,
        imageUrl = companyLogoImageUrl.orEmpty(),
        companyName = companyName,
        stageName = stageName,
        title = postingTitle,
    )
