package com.haphap.app.data.remote.datasource.api.search

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.search.PopularListResponseDto
import com.haphap.app.data.remote.dto.search.SearchResultListResponseDto
import com.haphap.app.data.remote.dto.search.SearchingListResponseDto

interface SearchDataSource {
    suspend fun getPopularList(): BaseResponse<PopularListResponseDto>
    suspend fun getSearchingList(q: String?): BaseResponse<SearchingListResponseDto>
    suspend fun getSearchResultList(
        q: String?,
        category: List<String>?,
        page: Int?,
        size: Int?,
    ): BaseResponse<SearchResultListResponseDto>
}
