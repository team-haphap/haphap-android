package com.haphap.app.data.repository.impl.search

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.local.datasource.api.LocalSearchDataSource
import com.haphap.app.data.mapper.search.toModel
import com.haphap.app.data.model.search.RecentSearchItemModel
import com.haphap.app.data.model.search.SearchPopularItemModel
import com.haphap.app.data.model.search.SearchResultPageModel
import com.haphap.app.data.model.search.SearchingModel
import com.haphap.app.data.remote.datasource.api.search.SearchDataSource
import com.haphap.app.data.remote.dto.checkData
import com.haphap.app.data.repository.api.search.SearchRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchRepositoryImpl @Inject constructor(
    private val localRecentSearchDataSource: LocalSearchDataSource,
    private val remoteSearchDataSource: SearchDataSource,
) : SearchRepository {
    override suspend fun saveRecentSearchItem(searchText: String): Result<Unit> =
        suspendRunCatching {
            localRecentSearchDataSource.setSearchKeyword(searchText = searchText)
        }

    override fun getRecentSearchItem(): Flow<List<RecentSearchItemModel>> =
        localRecentSearchDataSource.getSearchKeyword()
            .map { entities -> entities.map { it.toModel() } }

    override suspend fun deleteRecentSearchItem(id: Long): Result<Unit> =
        suspendRunCatching {
            localRecentSearchDataSource.deleteSearchKeyword(id)
        }

    override suspend fun getPopularList(): Result<List<SearchPopularItemModel>> =
        suspendRunCatching {
            remoteSearchDataSource.getPopularList().checkData().toModel()
        }

    override suspend fun getSearchingList(q: String?): Result<SearchingModel> =
        suspendRunCatching {
            remoteSearchDataSource.getSearchingList(q = q).checkData().toModel()
        }


    override suspend fun getSearchResultList(
        q: String?,
        category: List<String>?,
        page: Int?,
        size: Int?,
    ): Result<SearchResultPageModel> =
        suspendRunCatching {
            remoteSearchDataSource.getSearchResultList(
                q = q,
                category = category,
                page = page,
                size = size,
            ).checkData().toModel()
        }

}
