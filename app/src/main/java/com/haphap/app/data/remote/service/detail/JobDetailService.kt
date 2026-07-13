package com.haphap.app.data.remote.service.detail

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.detail.JobDetailDto
import com.haphap.app.data.remote.dto.detail.JobDetailStageListDto
import com.haphap.app.data.remote.dto.detail.JobDetailStageStatisticDto
import com.haphap.app.data.remote.dto.detail.JobDetailStageStatusListDto
import retrofit2.http.GET
import retrofit2.http.Path

interface JobDetailService {
    @GET("api/v1/postings/{postingId}/detail")
    suspend fun getJobPostingDetail(
        @Path("postingId") postingId: Int,
    ): BaseResponse<JobDetailDto>

    @GET("api/v1/postings/{postingId}/stages")
    suspend fun getJobPostingStages(
        @Path("postingId") postingId: Int,
    ): BaseResponse<JobDetailStageListDto>

    @GET("api/v1/postings/{postingId}/statistics")
    suspend fun getJobPostingStageStatuses(
        @Path("postingId") postingId: Int,
    ): BaseResponse<JobDetailStageStatusListDto>

    @GET("api/v1/postings/{postingId}/{stageId}/statistics")
    suspend fun getJobPostingStageStatistic(
        @Path("postingId") postingId: Int,
        @Path("stageId") stageId: Int,
    ): BaseResponse<JobDetailStageStatisticDto>
}
