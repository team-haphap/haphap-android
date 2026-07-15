package com.haphap.app.data.joblist

import com.haphap.app.data.repository.api.JobListRepository
import com.haphap.app.data.repository.impl.JobListRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class JobListRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindJobListRepository(
        jobListRepositoryImpl: JobListRepositoryImpl
    ): JobListRepository
}
