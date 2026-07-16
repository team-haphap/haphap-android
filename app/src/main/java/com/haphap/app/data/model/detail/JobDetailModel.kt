package com.haphap.app.data.model.detail

import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class JobDetailModel(
    val titleInfo: JobTitleModel = JobTitleModel(),
    val bannerImageUrl: String = "",
    val participant: JobParticipantModel = JobParticipantModel(),
    val reports: ImmutableList<JobStepReportModel> = persistentListOf(),
    val alarmEnabled: Boolean,
)
