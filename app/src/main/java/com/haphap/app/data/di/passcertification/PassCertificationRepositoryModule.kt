package com.haphap.app.data.di.passcertification

import com.haphap.app.data.repository.api.passcertification.PassCertificationRepository
import com.haphap.app.data.repository.impl.passcertification.PassCertificationRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PassCertificationRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindPassCertificationRepository(
        passCertificationRepositoryImpl: PassCertificationRepositoryImpl,
    ): PassCertificationRepository
}
