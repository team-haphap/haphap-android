package com.haphap.app.data.repository.api

import com.haphap.app.data.model.list.JobItemModel

interface JobListRepository {
    suspend fun getJobList(category: List<String>?): Result<List<JobItemModel>>
}
