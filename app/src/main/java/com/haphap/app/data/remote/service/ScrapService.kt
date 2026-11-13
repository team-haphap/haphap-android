package com.haphap.app.data.remote.service

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.scrap.ScrapListResponseDto
import com.haphap.app.data.remote.dto.scrap.ScrapResponseDto
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ScrapService {
    @GET("api/v1/my-page/scraps")
    suspend fun getScraps(): BaseResponse<ScrapListResponseDto>

    @POST("api/v1/scraps/{postingId}")
    suspend fun postScrap(
        @Path("postingId") postingId: Int,
    ): BaseResponse<ScrapResponseDto>

    @DELETE("api/v1/scraps/{postingId}")
    suspend fun deleteScrap(
        @Path("postingId") postingId: Int,
    ): BaseResponse<ScrapResponseDto>
}
