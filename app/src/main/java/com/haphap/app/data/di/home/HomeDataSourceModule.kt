package com.haphap.app.data.di.home

import com.haphap.app.data.remote.datasource.api.home.HomeDataSource
import com.haphap.app.data.remote.datasource.impl.home.HomeDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsHomeDataSource(
        homeDataSourceImpl: HomeDataSourceImpl
    ): HomeDataSource
}