package com.haphap.app.data.remote.datasource.api.mypage

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.mypage.AccountInfoResponseDto
import com.haphap.app.data.remote.dto.mypage.LeaveRequestDto
import com.haphap.app.data.remote.dto.mypage.LogoutRequestDto
import com.haphap.app.data.remote.dto.mypage.MyPageNicknameResponseDto
import com.haphap.app.data.remote.dto.mypage.MyPageResponseDto
import com.haphap.app.data.remote.dto.mypage.MyPageSummaryResponseDto
import com.haphap.app.data.remote.dto.mypage.MyRegistrationsPreviewResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response

interface MyPageDataSource{
    suspend fun getMyPage(): BaseResponse<MyPageResponseDto>
    suspend fun getMyPageSummary(): BaseResponse<MyPageSummaryResponseDto>
    suspend fun getMyRegistrationsPreview(): BaseResponse<MyRegistrationsPreviewResponseDto>
    suspend fun getAccountInfo(): BaseResponse<AccountInfoResponseDto>
    suspend fun getNicknameValidation(nickname: String): Response<BaseResponse<MyPageNicknameResponseDto>>
    suspend fun patchProfile(
        nickname: RequestBody?,
        profileImage: MultipartBody.Part?,
    ): BaseResponse<MyPageResponseDto>
    suspend fun deleteMember(requestDto: LeaveRequestDto): Response<Unit>
    suspend fun postLogout(requestDto: LogoutRequestDto): Response<Unit>
}
