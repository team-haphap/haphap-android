package com.haphap.app.data.di.home

import com.haphap.app.core.network.di.Auth
import com.haphap.app.data.remote.service.home.HomeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HomeServiceModule {
    @Provides
    @Singleton
    fun provideHomeBannerService(
        @Auth retrofit: Retrofit
    ): HomeService = retrofit.create()
}