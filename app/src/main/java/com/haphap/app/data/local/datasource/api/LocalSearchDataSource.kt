package com.haphap.app.data.local.datasource.api

import com.haphap.app.data.local.database.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

interface LocalSearchDataSource {
    suspend fun setSearchKeyword(searchText: String)
    fun getSearchKeyword(): Flow<List<RecentSearchEntity>>
    suspend fun deleteSearchKeyword(id: Long)
}
