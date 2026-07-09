package com.haphap.app.data.local.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haphap.app.data.local.database.RecentSearchEntity
import kotlinx.coroutines.flow.Flow

@Dao
abstract class RecentSearchDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insertRecentItem(recentSearchEntity: RecentSearchEntity)

    @Query("SELECT * FROM recent_search ORDER BY searchedAt DESC")
    abstract fun getRecentSearchItems(): Flow<List<RecentSearchEntity>>

    @Query("DELETE FROM recent_search WHERE searchText = :searchText")
    abstract suspend fun removeDuplicatedText(searchText: String)

    @Query("DELETE FROM recent_search WHERE id = :id")
    abstract suspend fun deleteRecentSearchItem(id: Long)

    @Query(
        """
    DELETE FROM recent_search
    WHERE searchText NOT IN (
        SELECT searchText
        FROM recent_search
        ORDER BY searchedAt DESC
        LIMIT $MAX_RECENT_SEARCH_COUNT
    )
    """
    )
    abstract suspend fun trimRecentSearchItems()

    companion object {
        private const val MAX_RECENT_SEARCH_COUNT = 5
    }

}
