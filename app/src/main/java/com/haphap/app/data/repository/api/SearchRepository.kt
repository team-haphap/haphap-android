package com.haphap.app.data.repository.api

import com.airbnb.lottie.L
import com.haphap.app.data.model.search.RecentSearchItemModel
import com.haphap.app.data.model.search.SearchResultPageModel
import com.haphap.app.data.model.search.SearchPopularItemModel
import com.haphap.app.data.model.search.SearchingModel
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    suspend fun saveRecentSearchItem(searchText: String): Result<Unit>
    fun getRecentSearchItem(): Flow<List<RecentSearchItemModel>>
    suspend fun deleteRecentSearchItem(id: Long): Result<Unit>
    suspend fun getPopularList(): Result<List<SearchPopularItemModel>>
    suspend fun getSearchingList(q: String?): Result<SearchingModel>
    suspend fun getSearchResultList(
        q: String?,
        category: List<String>?,
        page: Int?,
        size: Int?,
    ): Result<SearchResultPageModel>
}
