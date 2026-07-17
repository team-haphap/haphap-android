package com.haphap.app.data.joblist

import com.haphap.app.data.remote.service.JobListService
import com.haphap.app.data.remote.service.HomeService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object JobListServiceModule {
    @Provides
    @Singleton
    fun provideJobListService(
        retrofit: Retrofit
    ): JobListService = retrofit.create()
}
