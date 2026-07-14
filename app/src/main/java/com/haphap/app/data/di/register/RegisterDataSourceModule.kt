package com.haphap.app.data.di.register

import com.haphap.app.data.remote.datasource.api.register.RegisterDataSource
import com.haphap.app.data.remote.datasource.impl.register.RegisterDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RegisterDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindRegisterDataSource(
        registerDataSourceImpl: RegisterDataSourceImpl
    ): RegisterDataSource
}