package com.haphap.app.data.mapper.detail

import com.haphap.app.data.model.detail.JobDetailModel
import com.haphap.app.data.model.detail.JobParticipantModel
import com.haphap.app.data.model.detail.JobResultModel
import com.haphap.app.data.model.detail.JobResultTabModel
import com.haphap.app.data.model.detail.JobStepModel
import com.haphap.app.data.model.detail.JobStepReportModel
import com.haphap.app.data.model.detail.JobTitleModel
import com.haphap.app.data.remote.dto.detail.JobDetailDto
import com.haphap.app.data.remote.dto.detail.JobDetailStageListDto
import com.haphap.app.data.remote.dto.detail.JobDetailStageStatisticDto
import com.haphap.app.data.remote.dto.detail.JobDetailStageStatusListDto
import com.haphap.app.presentation.jobdetail.type.JobStepReportType
import com.haphap.app.presentation.jobdetail.type.JobStepStatus
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

fun JobDetailDto.toJobDetailModel(): JobDetailModel {
    return JobDetailModel(
        titleInfo = toJobTitleModel(),
        bannerImageUrl = companyImageUrl,
        participant = toJobParticipantModel(),
        reports = toJobStepReportModels(),
    )
}

fun JobDetailDto.toJobTitleModel(): JobTitleModel {
    return JobTitleModel(
        companyName = companyName,
        postingTitle = postingTitle,
        keywords = listOf(category, location, position).toImmutableList(),
        currentState = currentState,
    )
}

fun JobDetailDto.toJobParticipantModel(): JobParticipantModel {
    return JobParticipantModel(
        registeredCount = summary.registeredCount,
        profileImages = summary.profileImages.map { it.profileImageUrl }.toImmutableList(),
        additionalParticipantCount = summary.additionalParticipantCount,
    )
}

fun JobDetailDto.toJobStepReportModels(): ImmutableList<JobStepReportModel> {
    return registrations.map { dto ->
        JobStepReportModel(
            id = dto.registrationId,
            time = dto.feedCreatedAt.substring(startIndex = 11, endIndex = 16),
            nickName = dto.nickName,
            result = JobStepReportType.valueOf(dto.registrationResult),
            stage = dto.stage,
        )
    }.toImmutableList()
}

fun JobDetailStageListDto.toResultTabModels(): ImmutableList<JobResultTabModel> {
    return stages
        .sortedBy { it.orderIndex }
        .map { dto ->
            JobResultTabModel(
                stageId = dto.stageId,
                stageName = dto.stageName,
            )
        }
        .toImmutableList()
}

fun JobDetailStageStatusListDto.toJobStepModels(): ImmutableList<JobStepModel> {
    return stages.map { dto ->
        JobStepModel(
            stageId = dto.stageId,
            number = dto.orderIndex,
            stageName = dto.stageName,
            status = JobStepStatus.valueOf(dto.status),
        )
    }.toImmutableList()
}

fun JobDetailStageStatisticDto.toJobResultModel(): JobResultModel {
    return JobResultModel(
        passCount = passCount,
        failCount = failCount,
        pendingCount = pendingCount,
    )
}
