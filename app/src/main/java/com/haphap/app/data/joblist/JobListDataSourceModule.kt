package com.haphap.app.data.joblist

import com.haphap.app.data.remote.datasource.api.joblist.JobListDataSource
import com.haphap.app.data.remote.datasource.impl.joblist.JobListDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class JobListDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindJobListDataSource(
        jobListDataSourceImpl: JobListDataSourceImpl
    ): JobListDataSource
}
