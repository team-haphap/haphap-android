package com.haphap.app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.haphap.app.data.local.database.RecentSearchEntity

@Database(entities = [RecentSearchEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun recentSearchDao(): RecentSearchDao
}
