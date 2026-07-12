package com.haphap.app.data.remote.datasource.api.detail

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.detail.JobDetailDto

interface JobDetailDataSource {
    suspend fun getJobPostingDetail(postingId: Long): BaseResponse<JobDetailDto>
}