package com.haphap.app.data.di.passcertification

import com.haphap.app.data.remote.service.PassCertificationService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PassCertificationServiceModule {
    @Provides
    @Singleton
    fun providePassCertificationService(
        retrofit: Retrofit,
    ): PassCertificationService = retrofit.create()
}
