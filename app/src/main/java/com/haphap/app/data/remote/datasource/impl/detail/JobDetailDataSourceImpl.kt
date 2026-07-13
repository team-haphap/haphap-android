package com.haphap.app.data.remote.datasource.impl.detail

import com.haphap.app.data.remote.datasource.api.detail.JobDetailDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.detail.JobDetailDto
import com.haphap.app.data.remote.dto.detail.JobDetailStageStatusListDto
import com.haphap.app.data.remote.service.detail.JobDetailService
import jakarta.inject.Inject

class JobDetailDataSourceImpl @Inject constructor(
    private val jobDetailService: JobDetailService,
) : JobDetailDataSource {

    override suspend fun getJobPostingDetail(postingId: Int): BaseResponse<JobDetailDto> =
        jobDetailService.getJobPostingDetail(postingId)

    override suspend fun getJobPostingStageStatuses(postingId: Int): BaseResponse<JobDetailStageStatusListDto> =
        jobDetailService.getJobPostingStageStatuses(postingId)
}
