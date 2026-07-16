package com.haphap.app.data.di.alarm

import com.haphap.app.data.remote.datasource.api.alarm.AlarmDataSource
import com.haphap.app.data.remote.datasource.impl.alarm.AlarmDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AlarmDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindAlarmDataSource(
        alarmDataSourceImpl: AlarmDataSourceImpl
    ): AlarmDataSource
}
