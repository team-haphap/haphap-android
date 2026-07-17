package com.haphap.app.data.di.home

import com.haphap.app.data.remote.service.HomeService
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
    fun provideHomeService(
        retrofit: Retrofit
    ): HomeService = retrofit.create()
}
