package com.haphap.app.data.di.passcard

import com.haphap.app.data.repository.api.passcard.PassCardRepository
import com.haphap.app.data.repository.impl.passcard.PassCardRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PassCardRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPassCardRepository(
        passCardRepositoryImpl: PassCardRepositoryImpl,
    ): PassCardRepository
}