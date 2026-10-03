package com.haphap.app.data.remote.datasource.api.home

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.home.HomeBannerListDto
import com.haphap.app.data.remote.dto.home.HomeMyApplicationsResponseDto
import com.haphap.app.data.remote.dto.home.HomePopularResponseDto
import com.haphap.app.data.remote.dto.home.HomeRecentResponseDto

interface HomeDataSource {
    suspend fun getBannerList(): BaseResponse<HomeBannerListDto>
    suspend fun getRecentViews(): BaseResponse<HomeRecentResponseDto>
    suspend fun getPopularPostings(category: List<String>?): BaseResponse<HomePopularResponseDto>
    suspend fun getMyApplications(): BaseResponse<HomeMyApplicationsResponseDto>

}