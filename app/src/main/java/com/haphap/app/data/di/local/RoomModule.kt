package com.haphap.app.data.di.local

import android.content.Context
import androidx.room.Room.databaseBuilder
import com.haphap.app.data.local.database.AppDatabase
import com.haphap.app.data.local.database.RecentSearchDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDataBase(
        @ApplicationContext context: Context,
    ): AppDatabase = databaseBuilder(
        context.applicationContext,
        AppDatabase::class.java,
        "haphap_database"
    ).build()

    @Provides
    @Singleton
    fun provideRecentSearchDao(
        appDatabase: AppDatabase,
    ): RecentSearchDao = appDatabase.recentSearchDao()
}
