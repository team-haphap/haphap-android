package com.haphap.app.data.di.scrap

import com.haphap.app.data.remote.service.ScrapService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ScrapServiceModule {
    @Provides
    @Singleton
    fun provideScrapService(
        retrofit: Retrofit,
    ): ScrapService = retrofit.create()
}
