package com.haphap.app.data.repository.api.detail

import com.haphap.app.data.model.detail.JobDetailModel

interface JobDetailRepository {
    suspend fun getJobPostingDetail(postingId: Long): Result<JobDetailModel>
}