package com.haphap.app.data.di.viewcount

import com.haphap.app.data.remote.service.ViewCountService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ViewCountServiceModule {
    @Provides
    @Singleton
    fun provideViewCountService(
        retrofit: Retrofit,
    ): ViewCountService = retrofit.create()
}
