package com.haphap.app.data.di.search

import com.haphap.app.data.remote.datasource.api.search.SearchDataSource
import com.haphap.app.data.remote.datasource.impl.search.SearchDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindSearchDataSource(
        searchDataSourceImpl: SearchDataSourceImpl
    ): SearchDataSource
}
