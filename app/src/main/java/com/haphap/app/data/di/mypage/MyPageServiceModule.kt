package com.haphap.app.data.di.mypage

import com.haphap.app.data.remote.service.MyPageService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyPageServiceModule {
    @Provides
    @Singleton
    fun provideMyPageService(
        retrofit: Retrofit,
    ): MyPageService = retrofit.create()
}