package com.haphap.app.data.remote.datasource.api.search

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.search.PopularListResponseDto

interface SearchDataSource {
    suspend fun getPopularList(): BaseResponse<PopularListResponseDto>
}
