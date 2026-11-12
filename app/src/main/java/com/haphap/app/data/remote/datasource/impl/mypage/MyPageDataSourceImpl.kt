package com.haphap.app.data.remote.datasource.impl.mypage

import com.haphap.app.data.remote.datasource.api.mypage.MyPageDataSource
import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.mypage.LeaveRequestDto
import com.haphap.app.data.remote.dto.mypage.LogoutRequestDto
import com.haphap.app.data.remote.dto.mypage.MyPageNicknameResponseDto
import com.haphap.app.data.remote.dto.mypage.MyPageResponseDto
import com.haphap.app.data.remote.service.MyPageService
import jakarta.inject.Inject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

class MyPageDataSourceImpl @Inject constructor(
    private val myPageService: MyPageService,
) : MyPageDataSource {
    override suspend fun getMyPage(): BaseResponse<MyPageResponseDto> {
        return myPageService.getMyPage()
    }

    override suspend fun validateNickname(nickname: String): BaseResponse<MyPageNicknameResponseDto> =
        myPageService.validateNickname(nickname)

    override suspend fun updateProfile(
        nickname: RequestBody?,
        profileImage: MultipartBody.Part?,
    ): BaseResponse<MyPageResponseDto> =
        myPageService.updateProfile(nickname, profileImage)

    override suspend fun deleteMember(requestDto: LeaveRequestDto): Response<Unit> =
        myPageService.deleteMember(requestDto)

    override suspend fun postLogout(requestDto: LogoutRequestDto): Response<Unit> =
        myPageService.logout(requestDto)
}