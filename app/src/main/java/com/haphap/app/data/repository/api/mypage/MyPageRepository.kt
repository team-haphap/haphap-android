package com.haphap.app.data.repository.api.mypage

import com.haphap.app.data.model.mypage.MyPageModel

interface MyPageRepository {
    suspend fun getMyPage(): Result<MyPageModel>
}