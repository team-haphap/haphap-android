package com.haphap.app.data.di.mypage

import com.haphap.app.data.repository.api.mypage.MyPageRepository
import com.haphap.app.data.repository.impl.mypage.MyPageRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MyPageRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindMyPageRepository(
        myPageRepositoryImpl: MyPageRepositoryImpl,
    ): MyPageRepository
}