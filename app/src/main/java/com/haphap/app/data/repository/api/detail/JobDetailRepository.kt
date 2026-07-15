package com.haphap.app.data.repository.api.detail

import com.haphap.app.data.model.detail.JobDetailModel
import com.haphap.app.data.model.detail.JobResultModel
import com.haphap.app.data.model.detail.JobResultTabModel
import com.haphap.app.data.model.detail.JobStepModel

interface JobDetailRepository {
    suspend fun getJobPostingDetail(postingId: Int): Result<JobDetailModel>

    suspend fun getJobPostingStages(postingId: Int): Result<List<JobResultTabModel>>

    suspend fun getJobPostingStageStatuses(postingId: Int): Result<List<JobStepModel>>

    suspend fun getJobPostingStageStatistic(postingId: Int, stageId: Int): Result<JobResultModel>

    suspend fun setJobPostingAlarm(postingId: Int): Result<Unit>

    suspend fun deleteJobPostingAlarm(postingId: Int): Result<Unit>
}
