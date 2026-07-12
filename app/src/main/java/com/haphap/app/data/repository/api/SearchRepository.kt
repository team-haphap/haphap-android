package com.haphap.app.data.repository.api

import com.haphap.app.data.model.search.RecentSearchItemModel
import com.haphap.app.data.model.search.TrendJobItemModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun saveRecentSearchItem(searchText: String): Result<Unit>
    fun getRecentSearchItem(): Flow<List<RecentSearchItemModel>>
    suspend fun deleteRecentSearchItem(id: Long): Result<Unit>
    suspend fun getPopularList(): Result<List<TrendJobItemModel>>
}
