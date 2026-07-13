package com.haphap.app.data.remote.service.detail

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.detail.JobDetailDto
import com.haphap.app.data.remote.dto.detail.JobDetailStageStatusListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface JobDetailService {
    @GET("api/v1/postings/{postingId}/detail")
    suspend fun getJobPostingDetail(
        @Path("postingId") postingId: Int,
    ): BaseResponse<JobDetailDto>

    @GET("api/v1/postings/{postingId}/statistics")
    suspend fun getJobPostingStageStatuses(
        @Path("postingId") postingId: Int,
    ): BaseResponse<JobDetailStageStatusListDto>
}
