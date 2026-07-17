package com.haphap.app.data.di.register

import com.haphap.app.data.remote.service.RegisterService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import retrofit2.Retrofit
import retrofit2.create

@Module
@InstallIn(SingletonComponent::class)
object RegisterServiceModule {
    @Provides
    @Singleton
    fun provideRegisterService(
        retrofit: Retrofit
    ): RegisterService = retrofit.create()
}
