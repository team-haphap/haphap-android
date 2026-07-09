package com.haphap.app.data.repository.api.search

import com.haphap.app.data.model.search.RecentSearchItemModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun saveRecentSearchItem(searchText: String): Result<Unit>
    suspend fun getRecentSearchItem(): Result<Flow<List<RecentSearchItemModel>>>
    suspend fun deleteRecentSearchItem(id: Long): Result<Unit>
}
