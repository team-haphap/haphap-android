package com.haphap.app.data.remote.datasource.impl.search

import com.haphap.app.data.remote.datasource.api.search.SearchDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.search.PopularKeywordListResponseDto
import com.haphap.app.data.remote.dto.search.SearchExecutionRequestDto
import com.haphap.app.data.remote.dto.search.SearchResultListResponseDto
import com.haphap.app.data.remote.dto.search.SearchingListResponseDto
import com.haphap.app.data.remote.service.SearchService
import retrofit2.Response
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val searchService: SearchService,
) : SearchDataSource {

    override suspend fun getPopularKeywordList(): BaseResponse<PopularKeywordListResponseDto> {
        return searchService.getPopularKeywordList()
    }

    override suspend fun getSearchingList(q: String?): BaseResponse<SearchingListResponseDto> {
        return searchService.getAutoCompleteList(q = q)
    }

    override suspend fun getSearchResultList(
        q: String?,
        category: List<String>?,
        page: Int?,
        size: Int?,
    ): BaseResponse<SearchResultListResponseDto> {
        return searchService.getSearchResultList(
            q = q,
            category = category,
            page = page,
            size = size,
        )
    }

    override suspend fun postSearchExecution(request: SearchExecutionRequestDto): Response<Unit> =
        searchService.postSearchExecution(request)
}
