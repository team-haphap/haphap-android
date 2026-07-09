package com.haphap.app.data.di.search

import com.haphap.app.data.repository.api.auth.AuthRepository
import com.haphap.app.data.repository.api.search.SearchRepository
import com.haphap.app.data.repository.impl.auth.AuthRepositoryImpl
import com.haphap.app.data.repository.impl.search.SearchRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository
}
