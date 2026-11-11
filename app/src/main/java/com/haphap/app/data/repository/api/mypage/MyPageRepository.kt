package com.haphap.app.data.repository.api.mypage

import com.haphap.app.data.model.myapplication.MyApplicationFilter
import com.haphap.app.data.model.myapplication.MyApplicationListModel
import com.haphap.app.data.model.mypage.MyPageModel

interface MyPageRepository {
    suspend fun getMyPage(): Result<MyPageModel>
    suspend fun deleteMember(reason: String, etcReason: String?): Result<Unit>
    suspend fun postLogout(): Result<Unit>
    suspend fun getMyApplicationList(filter: MyApplicationFilter): Result<MyApplicationListModel>
}
