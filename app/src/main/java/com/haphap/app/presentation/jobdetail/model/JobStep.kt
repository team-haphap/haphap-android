package com.haphap.app.presentation.jobdetail.model

import com.haphap.app.presentation.jobdetail.type.JobStepStatus

data class JobStep(
    val number: Int,
    val stageName: String,
    val stateText: String,
    val status: JobStepStatus,
)