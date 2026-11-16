package com.haphap.app.core.security.di

import com.haphap.app.core.security.KeystoreTokenCipher
import com.haphap.app.core.security.TokenCipher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SecurityModule {

    @Binds
    @Singleton
    abstract fun bindTokenCipher(
        keystoreTokenCipher: KeystoreTokenCipher
    ): TokenCipher
}
