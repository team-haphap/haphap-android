package com.haphap.app.data.remote.datasource.impl.home

import com.haphap.app.data.remote.datasource.api.home.HomeDataSource
import com.haphap.app.data.remote.service.HomeService
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.home.HomeAnnouncementsResponseDto
import com.haphap.app.data.remote.dto.home.HomeBannerListDto
import com.haphap.app.data.remote.dto.home.HomeMyApplicationsResponseDto
import com.haphap.app.data.remote.dto.home.HomePopularResponseDto
import com.haphap.app.data.remote.dto.home.HomePostingsResponseDto
import com.haphap.app.data.remote.dto.home.HomeRecentResponseDto
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

    override suspend fun getRecentPostings(category: List<String>?): BaseResponse<HomePostingsResponseDto> {
        return homeService.getRecentPostings(category)
    }

    override suspend fun getRecentViews(): BaseResponse<HomeRecentResponseDto> {
        return homeService.getRecentViews()
    }

    override suspend fun getPopularPostings(category: List<String>?): BaseResponse<HomePopularResponseDto> {
        return homeService.getPopularPostings(category)
    }

    override suspend fun getMyApplications(): BaseResponse<HomeMyApplicationsResponseDto> {
        return homeService.getMyApplications()
    }
}
