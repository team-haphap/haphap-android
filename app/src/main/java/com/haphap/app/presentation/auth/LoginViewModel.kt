package com.haphap.app.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.core.state.UiState
import com.haphap.app.data.model.auth.KakaoLoginModel
import com.haphap.app.data.repository.api.auth.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<KakaoLoginModel>>(UiState.Idle)
    val loginState: StateFlow<UiState<KakaoLoginModel>> = _loginState.asStateFlow()

    fun kakaoLogin(kakaoAccessToken: String) {
        _loginState.value = UiState.Loading

        viewModelScope.launch {
            authRepository.postKakaoLogin(kakaoAccessToken)
                .onSuccess { model ->
                    _loginState.value = UiState.Success(model)
                }
                .onFailure { throwable ->
                    _loginState.value = UiState.Failure(
                        throwable.message ?: "로그인 처리 중 오류가 발생했습니다."
                    )
                }
        }
    }

    fun consumeFailure() {
        _loginState.value = UiState.Idle
    }
}