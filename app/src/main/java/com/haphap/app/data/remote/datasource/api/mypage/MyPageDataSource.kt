package com.haphap.app.data.remote.datasource.api.mypage

import com.haphap.app.data.remote.dto.BaseResponse
import com.haphap.app.data.remote.dto.mypage.LeaveRequestDto
import com.haphap.app.data.remote.dto.mypage.LogoutRequestDto
import com.haphap.app.data.remote.dto.mypage.MyPageResponseDto
import com.haphap.app.data.remote.dto.mypage.MyPageSummaryResponseDto
import com.haphap.app.data.remote.dto.mypage.MyRegistrationsPreviewResponseDto
import retrofit2.Response

interface MyPageDataSource{
    suspend fun getMyPage(): BaseResponse<MyPageResponseDto>
    suspend fun getMyPageSummary(): BaseResponse<MyPageSummaryResponseDto>
    suspend fun getMyRegistrationsPreview(): BaseResponse<MyRegistrationsPreviewResponseDto>
    suspend fun deleteMember(requestDto: LeaveRequestDto): Response<Unit>
    suspend fun postLogout(requestDto: LogoutRequestDto): Response<Unit>
}