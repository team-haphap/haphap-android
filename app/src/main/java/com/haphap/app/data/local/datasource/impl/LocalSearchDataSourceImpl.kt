package com.haphap.app.data.local.datasource.impl

import com.haphap.app.data.local.database.RecentSearchDao
import com.haphap.app.data.local.database.RecentSearchEntity
import com.haphap.app.data.local.datasource.api.LocalSearchDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalSearchDataSourceImpl @Inject constructor(
    private val recentSearchDao: RecentSearchDao,
) : LocalSearchDataSource {

    override suspend fun setSearchKeyword(searchText: String) {
        recentSearchDao.removeDuplicatedText(searchText)
        recentSearchDao.insertRecentItem(
            RecentSearchEntity(
                id = 0,
                searchText = searchText,
                searchedAt = System.currentTimeMillis()
            )
        )
        recentSearchDao.trimRecentSearchItems()
    }

    override fun getSearchKeyword(): Flow<List<RecentSearchEntity>> =
        recentSearchDao.getRecentSearchItems()


    override suspend fun deleteSearchKeyword(id: Long) {
        recentSearchDao.deleteRecentSearchItem(id)
    }

}
