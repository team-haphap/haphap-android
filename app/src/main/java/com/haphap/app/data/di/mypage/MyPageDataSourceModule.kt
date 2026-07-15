package com.haphap.app.data.di.mypage

import com.haphap.app.data.remote.datasource.api.mypage.MyPageDataSource
import com.haphap.app.data.remote.datasource.impl.mypage.MyPageDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class MyPageDataSourceModule {
    @Binds
    @Singleton
    abstract fun bindMyPageDataSource(
        myPageDataSourceImpl: MyPageDataSourceImpl,
    ): MyPageDataSource
}