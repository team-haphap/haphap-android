package com.haphap.app.data.repository.impl

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.local.datasource.api.LocalSearchDataSource
import com.haphap.app.data.mapper.search.toModel
import com.haphap.app.data.model.search.RecentSearchItemModel
import com.haphap.app.data.repository.api.SearchRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchRepositoryImpl @Inject constructor(
    private val localRecentSearchDataSource: LocalSearchDataSource,
): SearchRepository {
    override suspend fun saveRecentSearchItem(searchText: String): Result<Unit> =
        suspendRunCatching {
            localRecentSearchDataSource.setSearchKeyword(searchText = searchText)
        }

    override suspend fun getRecentSearchItem(): Result<Flow<List<RecentSearchItemModel>>> =
        suspendRunCatching {
            val data = localRecentSearchDataSource.getSearchKeyword()

            data.map { entities ->
                entities.map { it.toModel() }
            }
        }

    override suspend fun deleteRecentSearchItem(id: Long): Result<Unit> =
        suspendRunCatching {
            localRecentSearchDataSource.deleteSearchKeyword(id)
        }
}
