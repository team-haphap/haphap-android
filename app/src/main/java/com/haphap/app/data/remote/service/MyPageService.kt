package com.haphap.app.data.remote.service

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.mypage.MyPageResponseDto
import retrofit2.http.GET

interface MyPageService {
    @GET("api/v1/members/me")
    suspend fun getMyPage(): BaseResponse<MyPageResponseDto>
}