package com.haphap.app.data.repository.api.home

import com.haphap.app.data.model.home.BannerItemModel
import com.haphap.app.data.model.home.CountCardModel

interface HomeRepository {
    suspend fun getBannerList(): Result<List<BannerItemModel>>
    suspend fun getCountCard(): Result<CountCardModel>
}