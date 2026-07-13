package com.haphap.app.data.remote.service

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.search.AutoCompleteListDto
import com.haphap.app.data.remote.dto.search.PopularListResponseDto
import com.haphap.app.data.remote.dto.search.SearchResultListResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchService {
    @GET("/api/v1/search/popular")
    suspend fun getPopularList(): BaseResponse<PopularListResponseDto>

    @GET("api/v1/search/autocomplete")
    suspend fun getAutoCompleteList(
        @Query("q")
        q: String,
    ): BaseResponse<AutoCompleteListDto>

    @GET("/api/v1/search/postings")
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
}
