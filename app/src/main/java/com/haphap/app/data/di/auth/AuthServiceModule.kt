package com.haphap.app.data.di.auth

import com.haphap.app.core.network.di.Auth
import com.haphap.app.data.remote.service.AuthService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthServiceModule {
    @Provides
    @Singleton
    fun provideAuthService(
        @Auth retrofit: Retrofit
    ): AuthService = retrofit.create()
}
