package com.haphap.app.data.di.detail

import com.haphap.app.core.network.di.Auth
import com.haphap.app.data.remote.service.detail.JobDetailService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object JobDetailServiceModule {

    @Provides
    @Singleton
    fun provideJobDetailService(
        retrofit: Retrofit,
    ): JobDetailService = retrofit.create()
}