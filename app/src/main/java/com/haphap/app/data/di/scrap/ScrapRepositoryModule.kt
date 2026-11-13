package com.haphap.app.data.di.scrap

import com.haphap.app.data.repository.api.scrap.ScrapRepository
import com.haphap.app.data.repository.impl.scrap.ScrapRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ScrapRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindScrapRepository(
        scrapRepositoryImpl: ScrapRepositoryImpl,
    ): ScrapRepository
}
