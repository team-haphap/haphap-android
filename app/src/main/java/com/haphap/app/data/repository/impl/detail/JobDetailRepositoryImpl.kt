package com.haphap.app.data.repository.impl.detail

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.mapper.detail.toJobDetailModel
import com.haphap.app.data.mapper.detail.toJobStepModels
import com.haphap.app.data.model.detail.JobDetailModel
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

    override suspend fun getJobPostingStageStatuses(postingId: Int): Result<ImmutableList<JobStepModel>> =
        suspendRunCatching {
            jobDetailDataSource.getJobPostingStageStatuses(postingId)
                .checkData()
                .toJobStepModels()
        }
}
