package com.haphap.app.data.remote.service

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.home.HomeBannerListDto
import com.haphap.app.data.remote.dto.home.HomeMyApplicationsResponseDto
import com.haphap.app.data.remote.dto.home.HomePopularResponseDto
import com.haphap.app.data.remote.dto.home.HomeRecentResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("api/v1/banners")
    suspend fun getBannerList(): BaseResponse<HomeBannerListDto>

    @GET("api/v1/home/recent-views")
    suspend fun getRecentViews(): BaseResponse<HomeRecentResponseDto>

    @GET("api/v1/home/popular-postings")
    suspend fun getPopularPostings(
        @Query("category") category: List<String>? = null,
    ): BaseResponse<HomePopularResponseDto>

    @GET("api/v1/home/my-applications")
    suspend fun getMyApplications(): BaseResponse<HomeMyApplicationsResponseDto>
}
