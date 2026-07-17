package com.haphap.app.data.repository.impl.joblist

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.mapper.joblist.toModel
import com.haphap.app.data.model.list.JobItemModel
import com.haphap.app.data.remote.datasource.api.joblist.JobListDataSource
import com.haphap.app.data.remote.dto.checkData
import com.haphap.app.data.repository.api.joblist.JobListRepository
import jakarta.inject.Inject

class JobListRepositoryImpl @Inject constructor(
    private val jobListDataSource: JobListDataSource,
): JobListRepository {

    override suspend fun getJobList(category: List<String>?): Result<List<JobItemModel>> =
        suspendRunCatching {
            jobListDataSource.getJobList(category).checkData().toModel()
        }
}
