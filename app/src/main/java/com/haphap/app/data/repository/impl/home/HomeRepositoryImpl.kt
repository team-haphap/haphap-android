package com.haphap.app.data.repository.impl.home

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.mapper.home.toModel
import com.haphap.app.data.model.home.BannerItemModel
import com.haphap.app.data.model.home.CountCardModel
import com.haphap.app.data.remote.datasource.api.home.HomeDataSource
import com.haphap.app.data.remote.dto.checkData
import com.haphap.app.data.repository.api.home.HomeRepository
import jakarta.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val homeDataSource: HomeDataSource,
) : HomeRepository {

    override suspend fun getBannerList(): Result<List<BannerItemModel>> =
        suspendRunCatching {
            homeDataSource.getBannerList()
                .checkData()
                .banners
                .sortedBy { it.displayOrder }
                .map { it.toModel() }
        }

    override suspend fun getCountCard(): Result<CountCardModel> =
        suspendRunCatching {
            homeDataSource.getCountCard()
                .checkData()
                .toModel()
        }
}

