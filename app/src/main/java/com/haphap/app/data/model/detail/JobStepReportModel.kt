package com.haphap.app.data.model.detail

import com.haphap.app.presentation.jobdetail.type.JobStepReportType

data class JobStepReportModel(
    val id: Int,
    val time: String,
    val nickName: String,
    val result: JobStepReportType,
    val stage: String,
)