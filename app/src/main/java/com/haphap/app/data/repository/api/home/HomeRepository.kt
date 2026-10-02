package com.haphap.app.data.repository.api.home

import com.haphap.app.data.model.home.BannerItemModel
import com.haphap.app.data.model.home.CountCardModel
import com.haphap.app.data.model.home.MyApplicationCardModel
import com.haphap.app.data.model.home.PopularJobCardModel
import com.haphap.app.data.model.home.RecentCardModel
import com.haphap.app.data.model.home.RecentJobCardModel
import com.haphap.app.data.model.home.TodayExpectedCardModel

interface HomeRepository {
    suspend fun getBannerList(): Result<List<BannerItemModel>>
    suspend fun getCountCard(): Result<CountCardModel>
    suspend fun getAnnouncements(): Result<List<TodayExpectedCardModel>>
    suspend fun getRecentPostings(category: List<String>?): Result<List<RecentCardModel>>
    suspend fun getRecentViews(): Result<List<RecentJobCardModel>>
    suspend fun getPopularPostings(category: List<String>?): Result<List<PopularJobCardModel>>
    suspend fun getMyApplications(): Result<List<MyApplicationCardModel>>
}