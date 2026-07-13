package com.haphap.app.data.repository.api.detail

import com.haphap.app.data.model.detail.JobDetailModel
import com.haphap.app.data.model.detail.JobStepModel
import kotlinx.collections.immutable.ImmutableList

interface JobDetailRepository {
    suspend fun getJobPostingDetail(postingId: Int): Result<JobDetailModel>

    suspend fun getJobPostingStageStatuses(postingId: Int): Result<ImmutableList<JobStepModel>>
}
