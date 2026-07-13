package com.haphap.app.data.remote.datasource.api.detail

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.detail.JobDetailDto
import com.haphap.app.data.remote.dto.detail.JobDetailStageListDto
import com.haphap.app.data.remote.dto.detail.JobDetailStageStatisticDto
import com.haphap.app.data.remote.dto.detail.JobDetailStageStatusListDto

interface JobDetailDataSource {
    suspend fun getJobPostingDetail(postingId: Int): BaseResponse<JobDetailDto>

    suspend fun getJobPostingStages(postingId: Int): BaseResponse<JobDetailStageListDto>

    suspend fun getJobPostingStageStatuses(postingId: Int): BaseResponse<JobDetailStageStatusListDto>

    suspend fun getJobPostingStageStatistic(postingId: Int, stageId: Int): BaseResponse<JobDetailStageStatisticDto>
}
