package com.haphap.app.data.di.calendar

import com.haphap.app.data.remote.service.CalendarService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CalendarServiceModule {
    @Provides
    @Singleton
    fun provideCalendarService(
        retrofit: Retrofit,
    ): CalendarService = retrofit.create()
}