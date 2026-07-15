package com.haphap.app.data.remote.datasource.api.mypage

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.mypage.MyPageResponseDto

interface MyPageDataSource{
    suspend fun getMyPage(): BaseResponse<MyPageResponseDto>
}