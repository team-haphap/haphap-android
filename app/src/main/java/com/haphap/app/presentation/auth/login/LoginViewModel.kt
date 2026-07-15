package com.haphap.app.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.core.state.UiState
import com.haphap.app.data.model.auth.KakaoLoginModel
import com.haphap.app.data.repository.api.auth.AuthRepository
import com.haphap.app.presentation.auth.login.LoginContract.SideEffect.NavigateToSignUpComplete
import com.haphap.app.presentation.auth.login.LoginContract.SideEffect.OnShowToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<LoginContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onKakaoLoginButtonClick() {
        _uiState.update { it.copy(loginUiState = LoginUiState.Loading) }
    }

    fun kakaoLogin(kakaoAccessToken: String) = viewModelScope.launch {
        _uiState.update { it.copy(loginUiState = LoginUiState.Loading) }

        authRepository.postKakaoLogin(accessToken = kakaoAccessToken)
            .onSuccess { model ->
                _uiState.update { it.copy(loginUiState = LoginUiState.Success) }
                _sideEffect.send(NavigateToSignUpComplete(model.name))
            }
            .onFailure { throwable ->
                _uiState.update { it.copy(loginUiState = LoginUiState.Failure) }
                _sideEffect.send(
                    OnShowToast(throwable.message ?: "로그인 처리 중 오류가 발생했습니다.")
                )
            }
    }
}
