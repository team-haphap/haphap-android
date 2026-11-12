package com.haphap.app.data.repository.api.mypage

import com.haphap.app.data.model.mypage.MyPageModel
import com.haphap.app.data.model.mypage.MyPageSummaryModel
import com.haphap.app.data.model.mypage.MyRecentApplicationModel

interface MyPageRepository {
    suspend fun getMyPage(): Result<MyPageModel>
    suspend fun getMyPageSummary(): Result<MyPageSummaryModel>
    suspend fun getMyRegistrationsPreview(): Result<List<MyRecentApplicationModel>>
    suspend fun deleteMember(reason: String, etcReason: String?): Result<Unit>
    suspend fun postLogout(): Result<Unit>
}