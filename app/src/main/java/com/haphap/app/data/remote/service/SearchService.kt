package com.haphap.app.data.remote.service

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.search.PopularListResponseDto
import retrofit2.http.GET

interface SearchService {
    @GET("/api/v1/search/popular")
    suspend fun getPopularList(): BaseResponse<PopularListResponseDto>
}
