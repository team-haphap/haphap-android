package com.haphap.app.data.di.passcard

import com.haphap.app.data.remote.datasource.api.passcard.PassCardDataSource
import com.haphap.app.data.remote.datasource.impl.passcard.PassCardDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PassCardDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindPassCardDataSource(
        passCardDataSourceImpl: PassCardDataSourceImpl,
    ): PassCardDataSource
}