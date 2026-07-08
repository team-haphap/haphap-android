package com.haphap.app.data.model.detail

import com.haphap.app.presentation.jobdetail.type.JobStepStatus

data class JobStepModel(
    val number: Int,
    val stageName: String,
    val status: JobStepStatus,
)