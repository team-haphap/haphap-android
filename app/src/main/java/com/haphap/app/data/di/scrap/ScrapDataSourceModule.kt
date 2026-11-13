package com.haphap.app.data.di.scrap

import com.haphap.app.data.remote.datasource.api.scrap.ScrapDataSource
import com.haphap.app.data.remote.datasource.impl.scrap.ScrapDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ScrapDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindScrapDataSource(
        scrapDataSourceImpl: ScrapDataSourceImpl,
    ): ScrapDataSource
}
