package com.haphap.app.data.di.viewcount

import com.haphap.app.data.remote.datasource.api.viewcount.ViewCountDataSource
import com.haphap.app.data.remote.datasource.impl.viewcount.ViewCountDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ViewCountDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindViewCountDataSource(
        viewCountDataSourceImpl: ViewCountDataSourceImpl,
    ): ViewCountDataSource
}
