package com.haphap.app.data.repository.api.home

import com.haphap.app.data.model.home.BannerItemModel

interface HomeRepository {
    suspend fun getBannerList(): Result<List<BannerItemModel>>
}