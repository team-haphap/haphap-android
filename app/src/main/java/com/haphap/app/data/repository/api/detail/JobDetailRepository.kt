package com.haphap.app.data.repository.api.detail

import com.haphap.app.data.model.detail.JobDetailModel
import com.haphap.app.data.model.detail.JobResultModel
import com.haphap.app.data.model.detail.JobResultTabModel
import com.haphap.app.data.model.detail.JobStepModel
import kotlinx.collections.immutable.ImmutableList

interface JobDetailRepository {
    suspend fun getJobPostingDetail(postingId: Int): Result<JobDetailModel>

    suspend fun getJobPostingStages(postingId: Int): Result<ImmutableList<JobResultTabModel>>

    suspend fun getJobPostingStageStatuses(postingId: Int): Result<ImmutableList<JobStepModel>>

    suspend fun getJobPostingStageStatistic(postingId: Int, stageId: Int): Result<JobResultModel>

    suspend fun setJobPostingAlarm(postingId: Int): Result<String>

    suspend fun deleteJobPostingAlarm(postingId: Int): Result<String>

    suspend fun recordView(postingId: Int): Result<Unit>
}
