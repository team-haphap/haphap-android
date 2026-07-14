package com.haphap.app.data.di.search

import com.haphap.app.data.remote.service.SearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SearchServiceModule {
    @Provides
    @Singleton
    fun provideSearchService(
        retrofit: Retrofit
    ): SearchService = retrofit.create()
}
