package com.haphap.app.data.remote.datasource.api.mypage

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.mypage.AccountInfoResponseDto
import com.haphap.app.data.remote.dto.mypage.LeaveRequestDto
import com.haphap.app.data.remote.dto.mypage.LogoutRequestDto
import com.haphap.app.data.remote.dto.mypage.MyPageResponseDto
import retrofit2.Response

interface MyPageDataSource{
    suspend fun getMyPage(): BaseResponse<MyPageResponseDto>
    suspend fun getAccountInfo(): BaseResponse<AccountInfoResponseDto>
    suspend fun deleteMember(requestDto: LeaveRequestDto): Response<Unit>
    suspend fun postLogout(requestDto: LogoutRequestDto): Response<Unit>
}
