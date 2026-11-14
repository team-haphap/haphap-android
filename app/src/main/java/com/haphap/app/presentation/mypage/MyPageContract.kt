package com.haphap.app.presentation.mypage

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.mypage.MyRecentApplicationModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface MyPageContract {
    @Immutable
    data class State(
        val profileUrl: String = "",
        val nickNameText: String = "",
        val point: Int = 0,
        val alarmCount: Int = 0,
        val rejectedCertificationCount: Int = 0,
        val approvedCertificationCount: Int = 0,
        val hasUpcomingAnnouncement: Boolean = false,
        val applicationList: ImmutableList<MyRecentApplicationModel> = persistentListOf(),
        val myPageUiState: MyPageUiState = MyPageUiState.Idle,
    )
}

sealed interface MyPageUiState {
    data object Idle : MyPageUiState
    data object Loading : MyPageUiState
    data object Success : MyPageUiState
    data class Failure(
        val msg: String,
    ) : MyPageUiState
}
