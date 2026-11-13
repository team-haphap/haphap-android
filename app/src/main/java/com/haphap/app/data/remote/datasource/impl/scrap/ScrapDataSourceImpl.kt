package com.haphap.app.data.remote.datasource.impl.scrap

import com.haphap.app.data.remote.datasource.api.scrap.ScrapDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.scrap.ScrapListResponseDto
import com.haphap.app.data.remote.dto.scrap.ScrapResponseDto
import com.haphap.app.data.remote.service.ScrapService
import javax.inject.Inject

class ScrapDataSourceImpl @Inject constructor(
    private val scrapService: ScrapService,
) : ScrapDataSource {
    override suspend fun getScraps(): BaseResponse<ScrapListResponseDto> =
        scrapService.getScraps()

    override suspend fun setScrap(postingId: Int): BaseResponse<ScrapResponseDto> =
        scrapService.setScrap(postingId)

    override suspend fun deleteScrap(postingId: Int): BaseResponse<ScrapResponseDto> =
        scrapService.deleteScrap(postingId)
}
