package com.haphap.app.data.di.detail

import com.haphap.app.data.repository.api.detail.JobDetailRepository
import com.haphap.app.data.repository.impl.detail.JobDetailRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class JobDetailRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindJobDetailRepository(
        jobDetailRepositoryImpl: JobDetailRepositoryImpl,
    ): JobDetailRepository
}