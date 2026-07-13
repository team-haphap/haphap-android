package com.haphap.app.presentation.mypage

import androidx.compose.runtime.Immutable

sealed interface MyPageContract {
    @Immutable
    data class State(
        val profileUrl: String = "",
        val nameText: String = "",
        val nickNameText: String = "",
        val emailText: String = "",
    )
}