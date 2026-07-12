package com.haphap.app.data.remote.service.home

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.home.HomeAnnouncementsResponseDto
import com.haphap.app.data.remote.dto.home.HomeBannerListDto
import com.haphap.app.data.remote.dto.home.HomePostingsResponseDto
import com.haphap.app.data.remote.dto.home.HomeTodayResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HomeService {
    @GET("api/v1/banners")
    suspend fun getBannerList(): BaseResponse<HomeBannerListDto>

    @GET("api/v1/postings/today")
    suspend fun getCountCard(): BaseResponse<HomeTodayResponseDto>

    @GET("api/v1/postings/announcements")
    suspend fun getAnnouncements(): BaseResponse<HomeAnnouncementsResponseDto>

    @GET("api/v1/postings")
    suspend fun getRecentPostings(
        @Query("category") category: String? = null
    ): BaseResponse<HomePostingsResponseDto>
}