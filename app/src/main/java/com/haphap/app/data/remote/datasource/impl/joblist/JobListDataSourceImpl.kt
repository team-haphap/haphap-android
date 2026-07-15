package com.haphap.app.data.remote.datasource.impl.joblist

import com.haphap.app.data.remote.datasource.api.joblist.JobListDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.joblist.JobListResponseDto
import com.haphap.app.data.remote.service.JobListService
import jakarta.inject.Inject

class JobListDataSourceImpl @Inject constructor(
    private val jobListService: JobListService,
) : JobListDataSource {

    override suspend fun getJobList(category: List<String>?): BaseResponse<JobListResponseDto> {
        return jobListService.getJobList(category)
    }

}
