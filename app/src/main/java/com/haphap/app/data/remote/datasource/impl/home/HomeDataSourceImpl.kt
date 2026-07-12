package com.haphap.app.data.remote.datasource.impl.home

import com.haphap.app.data.remote.datasource.api.home.HomeDataSource
import com.haphap.app.data.remote.service.home.HomeService
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.home.HomeAnnouncementsResponseDto
import com.haphap.app.data.remote.dto.home.HomeBannerListDto
import com.haphap.app.data.remote.dto.home.HomeTodayResponseDto
import jakarta.inject.Inject

class HomeDataSourceImpl @Inject constructor(
    private val homeService: HomeService,
) : HomeDataSource {

    override suspend fun getBannerList(): BaseResponse<HomeBannerListDto> {
        return homeService.getBannerList()
    }

    override suspend fun getCountCard(): BaseResponse<HomeTodayResponseDto> {
        return homeService.getCountCard()
    }

    override suspend fun getAnnouncements(): BaseResponse<HomeAnnouncementsResponseDto> {
        return homeService.getAnnouncements()
    }

}