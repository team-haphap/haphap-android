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
        val myPageUiState: MyPageUiState<MyPageModel> = MyPageUiState.Idle,
    )
}

sealed interface MyPageUiState<out T> {
    data object Idle : MyPageUiState<Nothing>
    data object Loading : MyPageUiState<Nothing>
    data class Success<T>(
        val data: T,
    ) : MyPageUiState<T>
    data class Failure(
        val msg: String,
    ) : MyPageUiState<Nothing>
}