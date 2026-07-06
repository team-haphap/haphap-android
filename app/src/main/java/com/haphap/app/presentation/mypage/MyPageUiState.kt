package com.haphap.app.presentation.mypage

import androidx.compose.runtime.Immutable

@Immutable
data class MyPageUiState(
    val profileUrl: String = "",
    val nameText: String = "",
    val nickNameText: String = "",
    val emailText: String = "",
)