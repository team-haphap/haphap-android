package com.haphap.app.data.di.calendar

import com.haphap.app.data.remote.datasource.api.calendar.CalendarDataSource
import com.haphap.app.data.remote.datasource.impl.calendar.CalendarDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class CalendarDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindCalendarDataSource(
        calendarDataSourceImpl: CalendarDataSourceImpl,
    ): CalendarDataSource
}