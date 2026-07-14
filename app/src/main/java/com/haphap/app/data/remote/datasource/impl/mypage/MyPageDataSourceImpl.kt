package com.haphap.app.data.remote.datasource.impl.mypage

import com.haphap.app.data.remote.datasource.api.mypage.MyPageDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.mypage.MyPageResponseDto
import com.haphap.app.data.remote.service.MyPageService
import jakarta.inject.Inject

class MyPageDataSourceImpl @Inject constructor(
    private val myPageService: MyPageService,
) : MyPageDataSource {
    override suspend fun getMyPage(): BaseResponse<MyPageResponseDto> {
        return myPageService.getMyPage()
    }
}