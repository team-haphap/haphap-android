package com.haphap.app.data.di.local

import com.haphap.app.data.local.datasource.api.LocalSearchDataSource
import com.haphap.app.data.local.datasource.api.LocalTokenDataSource
import com.haphap.app.data.local.datasource.impl.LocalSearchDataSourceImpl
import com.haphap.app.data.local.datasource.impl.LocalTokenDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocalDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindLocalTokenDataSource(
        localTokenDataSourceImpl: LocalTokenDataSourceImpl
    ): LocalTokenDataSource

    @Binds
    @Singleton
    abstract fun bindLocalRecentSearchDataSource(
        localRecentSearchDataSourceImpl: LocalSearchDataSourceImpl
    ): LocalSearchDataSource
}
