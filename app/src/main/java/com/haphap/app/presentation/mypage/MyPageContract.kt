package com.haphap.app.presentation.mypage

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.mypage.MyPageModel

sealed interface MyPageContract {
    @Immutable
    data class State(
        val profileUrl: String = "",
        val nameText: String = "",
        val nickNameText: String = "",
        val emailText: String = "",
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