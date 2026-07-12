package com.haphap.app.data.remote.datasource.impl.home

import com.haphap.app.data.remote.datasource.api.home.HomeDataSource
import com.haphap.app.data.remote.service.home.HomeService
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.home.HomeBannerListDto
import jakarta.inject.Inject

class HomeDataSourceImpl @Inject constructor(
    private val homeBannerService: HomeService,
) : HomeDataSource {

    override suspend fun getBannerList(): BaseResponse<HomeBannerListDto> {
        return homeBannerService.getBannerList()
    }
}