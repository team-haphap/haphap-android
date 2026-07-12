package com.haphap.app.data.remote.service.home

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.home.HomeBannerListDto
import retrofit2.http.GET

interface HomeService {
    @GET("api/v1/banners")
    suspend fun getBannerList(): BaseResponse<HomeBannerListDto>
}