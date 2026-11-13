package com.haphap.app.data.remote.service

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.search.PopularKeywordListResponseDto
import com.haphap.app.data.remote.dto.search.SearchExecutionRequestDto
import com.haphap.app.data.remote.dto.search.SearchResultListResponseDto
import com.haphap.app.data.remote.dto.search.SearchingListResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface SearchService {
    @GET("api/v1/search/popular-keywords")
    suspend fun getPopularKeywordList(): BaseResponse<PopularKeywordListResponseDto>

    @GET("api/v1/search/autocomplete")
    suspend fun getAutoCompleteList(
        @Query("q")
        q: String? = null,
    ): BaseResponse<SearchingListResponseDto>

    @GET("api/v1/search/postings")
    suspend fun getSearchResultList(
        @Query("q")
        q: String? = null,
        @Query("category")
        category: List<String>? = null,
        @Query("page")
        page: Int? = null,
        @Query("size")
        size: Int? = null,
    ): BaseResponse<SearchResultListResponseDto>

    @POST("api/v1/search/executions")
    suspend fun postSearchExecution(
        @Body request: SearchExecutionRequestDto,
    ): Response<Unit>
}
