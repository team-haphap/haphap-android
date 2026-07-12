package com.haphap.app.data.remote.datasource.api.home

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.home.HomeBannerListDto

interface HomeDataSource {
    suspend fun getBannerList(): BaseResponse<HomeBannerListDto>
}