package com.haphap.app.data.di.viewcount

import com.haphap.app.data.repository.api.viewcount.ViewCountRepository
import com.haphap.app.data.repository.impl.viewcount.ViewCountRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class ViewCountRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindViewCountRepository(
        viewCountRepositoryImpl: ViewCountRepositoryImpl,
    ): ViewCountRepository
}
