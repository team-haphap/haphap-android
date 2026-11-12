package com.haphap.app.data.di.passcertification

import com.haphap.app.data.remote.datasource.api.passcertification.PassCertificationDataSource
import com.haphap.app.data.remote.datasource.impl.passcertification.PassCertificationDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class PassCertificationDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindPassCertificationDataSource(
        passCertificationDataSourceImpl: PassCertificationDataSourceImpl,
    ): PassCertificationDataSource
}
