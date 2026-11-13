package com.haphap.app.data.remote.datasource.api.search

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.search.PopularKeywordListResponseDto
import com.haphap.app.data.remote.dto.search.SearchExecutionRequestDto
import com.haphap.app.data.remote.dto.search.SearchResultListResponseDto
import com.haphap.app.data.remote.dto.search.SearchingListResponseDto
import retrofit2.Response

interface SearchDataSource {
    suspend fun getPopularKeywordList(): BaseResponse<PopularKeywordListResponseDto>
    suspend fun getSearchingList(q: String?): BaseResponse<SearchingListResponseDto>
    suspend fun getSearchResultList(
        q: String?,
        category: List<String>?,
        page: Int?,
        size: Int?,
    ): BaseResponse<SearchResultListResponseDto>
    suspend fun postSearchExecution(request: SearchExecutionRequestDto): Response<Unit>
}
