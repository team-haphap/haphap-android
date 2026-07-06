package com.haphap.app.presentation.mypage

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(

) : ViewModel() {
    private val _uiState = MutableStateFlow(
        MyPageUiState(
            nickNameText = "익명의죠르디",
            emailText = "yeonsoo1234@naver.com",
        ),
    )
    val uiState: StateFlow<MyPageUiState> = _uiState.asStateFlow()
}