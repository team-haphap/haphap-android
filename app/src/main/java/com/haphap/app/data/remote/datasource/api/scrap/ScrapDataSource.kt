package com.haphap.app.data.remote.datasource.api.scrap

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.scrap.ScrapListResponseDto
import com.haphap.app.data.remote.dto.scrap.ScrapResponseDto

interface ScrapDataSource {
    suspend fun getScraps(): BaseResponse<ScrapListResponseDto>

    suspend fun setScrap(postingId: Int): BaseResponse<ScrapResponseDto>

    suspend fun deleteScrap(postingId: Int): BaseResponse<ScrapResponseDto>
}
