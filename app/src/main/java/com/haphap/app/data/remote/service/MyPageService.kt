package com.haphap.app.data.remote.service

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.mypage.LeaveRequestDto
import com.haphap.app.data.remote.dto.mypage.LogoutRequestDto
import com.haphap.app.data.remote.dto.mypage.MyPageNicknameResponseDto
import com.haphap.app.data.remote.dto.mypage.MyPageResponseDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Query

interface MyPageService {
    @GET("api/v1/members/me")
    suspend fun getMyPage(): BaseResponse<MyPageResponseDto>

    @GET("api/v1/members/me/nickname/validation")
    suspend fun validateNickname(
        @Query("nickname") nickname: String,
    ): BaseResponse<MyPageNicknameResponseDto>

    @HTTP(method = "DELETE", path = "api/v1/members/me", hasBody = true)
    suspend fun deleteMember(@Body request: LeaveRequestDto): Response<Unit>

    @POST("api/v1/auth/logout")
    suspend fun logout(@Body request: LogoutRequestDto): Response<Unit>
}