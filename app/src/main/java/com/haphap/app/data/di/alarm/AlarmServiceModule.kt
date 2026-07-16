package com.haphap.app.data.di.alarm

import com.haphap.app.data.remote.service.AlarmService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AlarmServiceModule {
    @Provides
    @Singleton
    fun provideAlarmService(
        retrofit: Retrofit
    ): AlarmService = retrofit.create()
}
