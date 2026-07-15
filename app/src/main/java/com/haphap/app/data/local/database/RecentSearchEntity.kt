package com.haphap.app.data.local.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recent_search")
data class RecentSearchEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val searchText: String,
    val searchedAt: Long,
)
