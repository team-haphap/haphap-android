package com.haphap.app.data.remote.service

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.joblist.JobListResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface JobListService {

    @GET("api/v1/postings/all")
    suspend fun getJobList(
        @Query("category") category: List<String>? = null
    ): BaseResponse<JobListResponseDto>

}
