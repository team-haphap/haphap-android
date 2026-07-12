package com.haphap.app.data.remote.datasource.impl.search

import com.haphap.app.data.remote.datasource.api.search.SearchDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.search.PopularListResponseDto
import com.haphap.app.data.remote.service.SearchService
import javax.inject.Inject

class SearchDataSourceImpl @Inject constructor(
    private val searchService: SearchService,
) : SearchDataSource {

    override suspend fun getPopularList(): BaseResponse<PopularListResponseDto> {
        return searchService.getPopularList()
    }
}
