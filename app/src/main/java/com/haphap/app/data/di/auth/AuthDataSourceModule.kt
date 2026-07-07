package com.haphap.app.data.di.auth

import com.haphap.app.data.remote.datasource.api.auth.AuthDataSource
import com.haphap.app.data.remote.datasource.impl.auth.AuthDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindAuthDataSource(
        authDataSourceImpl: AuthDataSourceImpl
    ): AuthDataSource
}