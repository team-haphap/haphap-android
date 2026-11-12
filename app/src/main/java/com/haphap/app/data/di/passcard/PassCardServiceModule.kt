package com.haphap.app.data.di.passcard

import com.haphap.app.data.remote.service.PassCardService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PassCardServiceModule {
    @Provides
    @Singleton
    fun providePassCardService(
        retrofit: Retrofit
    ): PassCardService = retrofit.create()
}