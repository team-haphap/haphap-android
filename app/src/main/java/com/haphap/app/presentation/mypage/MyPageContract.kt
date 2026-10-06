package com.haphap.app.presentation.mypage

import androidx.compose.runtime.Immutable

sealed interface MyPageContract {
    @Immutable
    data class State(
        val profileUrl: String = "",
        val nameText: String = "",
        val nickNameText: String = "",
        val emailText: String = "",
        val myPageUiState: MyPageUiState = MyPageUiState.Idle,
    )

    sealed class SideEffect {
        data object NavigateToHome : SideEffect()
        data object NavigateToSetting : SideEffect()
    }
}

sealed interface MyPageUiState {
    data object Idle : MyPageUiState
    data object Loading : MyPageUiState
    data object Success : MyPageUiState
    data class Failure(
        val msg: String,
    ) : MyPageUiState
}