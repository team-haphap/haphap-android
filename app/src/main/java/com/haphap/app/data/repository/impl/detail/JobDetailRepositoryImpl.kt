package com.haphap.app.data.repository.impl.detail

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.mapper.detail.toJobDetailModel
import com.haphap.app.data.mapper.detail.toJobResultModel
import com.haphap.app.data.mapper.detail.toJobStepModels
import com.haphap.app.data.mapper.detail.toResultTabModels
import com.haphap.app.data.model.detail.JobDetailModel
import com.haphap.app.data.model.detail.JobResultModel
import com.haphap.app.data.model.detail.JobResultTabModel
import com.haphap.app.data.model.detail.JobStepModel
import com.haphap.app.data.remote.datasource.api.detail.JobDetailDataSource
import com.haphap.app.data.remote.dto.checkData
import com.haphap.app.data.repository.api.detail.JobDetailRepository
import javax.inject.Inject
import kotlinx.collections.immutable.ImmutableList

class JobDetailRepositoryImpl @Inject constructor(
    private val jobDetailDataSource: JobDetailDataSource,
) : JobDetailRepository {

    override suspend fun getJobPostingDetail(postingId: Int): Result<JobDetailModel> =
        suspendRunCatching {
            jobDetailDataSource.getJobPostingDetail(postingId)
                .checkData()
                .toJobDetailModel()
        }

    override suspend fun getJobPostingStages(postingId: Int): Result<ImmutableList<JobResultTabModel>> =
        suspendRunCatching {
            jobDetailDataSource.getJobPostingStages(postingId)
                .checkData()
                .toResultTabModels()
        }

    override suspend fun getJobPostingStageStatuses(postingId: Int): Result<ImmutableList<JobStepModel>> =
        suspendRunCatching {
            jobDetailDataSource.getJobPostingStageStatuses(postingId)
                .checkData()
                .toJobStepModels()
        }

    override suspend fun getJobPostingStageStatistic(postingId: Int, stageId: Int): Result<JobResultModel> =
        suspendRunCatching {
            jobDetailDataSource.getJobPostingStageStatistic(postingId, stageId)
                .checkData()
                .toJobResultModel()
        }

    override suspend fun setJobPostingAlarm(postingId: Int): Result<String> =
        suspendRunCatching {
            val response = jobDetailDataSource.setAlarms(postingId)
            check(response.status == HTTP_OK) { response.message }
            response.message
        }

    override suspend fun deleteJobPostingAlarm(postingId: Int): Result<String> =
        suspendRunCatching {
            val response = jobDetailDataSource.deleteAlarms(postingId)
            check(response.status == HTTP_OK) { response.message }
            response.message
        }

    override suspend fun recordView(postingId: Int): Result<Unit> =
        suspendRunCatching {
            jobDetailDataSource.recordView(postingId)
        }

    companion object {
        private const val HTTP_OK = 200
    }
}
