package com.haphap.app.data.repository.impl.mypage

import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.mapper.mypage.toModel
import com.haphap.app.data.model.mypage.MyPageModel
import com.haphap.app.data.remote.datasource.api.mypage.MyPageDataSource
import com.haphap.app.data.remote.dto.checkData
import com.haphap.app.data.repository.api.mypage.MyPageRepository
import jakarta.inject.Inject

class MyPageRepositoryImpl @Inject constructor(
    private val myPageDataSource: MyPageDataSource,
): MyPageRepository {
    override suspend fun getMyPage(): Result<MyPageModel> =
        suspendRunCatching {
            val response = myPageDataSource.getMyPage().checkData()
            response.toModel()
        }
}