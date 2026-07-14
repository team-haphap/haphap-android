package com.haphap.app.data.di.detail

import com.haphap.app.data.remote.datasource.api.detail.JobDetailDataSource
import com.haphap.app.data.remote.datasource.impl.detail.JobDetailDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class JobDetailDataSourceModule {

    @Binds
    @Singleton
    abstract fun bindJobDetailDataSource(
        jobDetailDataSourceImpl: JobDetailDataSourceImpl,
    ): JobDetailDataSource
}