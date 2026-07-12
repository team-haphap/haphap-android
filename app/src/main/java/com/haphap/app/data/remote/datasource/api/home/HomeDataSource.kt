package com.haphap.app.data.remote.datasource.api.home

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.home.HomeAnnouncementsResponseDto
import com.haphap.app.data.remote.dto.home.HomeBannerListDto
import com.haphap.app.data.remote.dto.home.HomeTodayResponseDto

interface HomeDataSource {
    suspend fun getBannerList(): BaseResponse<HomeBannerListDto>
    suspend fun getCountCard(): BaseResponse<HomeTodayResponseDto>
    suspend fun getAnnouncements(): BaseResponse<HomeAnnouncementsResponseDto>
}