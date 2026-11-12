package com.haphap.app.data.remote.datasource.api.mypage

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.mypage.LeaveRequestDto
import com.haphap.app.data.remote.dto.mypage.LogoutRequestDto
import com.haphap.app.data.remote.dto.mypage.MyPageNicknameResponseDto
import com.haphap.app.data.remote.dto.mypage.MyPageResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface MyPageDataSource{
    suspend fun getMyPage(): BaseResponse<MyPageResponseDto>
    suspend fun validateNickname(nickname: String): Response<BaseResponse<MyPageNicknameResponseDto>>
    suspend fun updateProfile(
        nickname: RequestBody?,
        profileImage: MultipartBody.Part?,
    ): BaseResponse<MyPageResponseDto>
    suspend fun deleteMember(requestDto: LeaveRequestDto): Response<Unit>
    suspend fun postLogout(requestDto: LogoutRequestDto): Response<Unit>
}