package com.haphap.app.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.alarm.AlarmRepository
import com.haphap.app.data.repository.api.auth.AuthRepository
import com.haphap.app.presentation.auth.login.LoginContract.SideEffect.NavigateToSignUpComplete
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val alarmRepository: AlarmRepository,
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
                alarmRepository.registerDeviceId()
                _uiState.update { it.copy(loginUiState = LoginUiState.Success) }
                _sideEffect.send(NavigateToSignUpComplete(model.name))
            }
            .onFailure { throwable ->
                _uiState.update {
                    it.copy(loginUiState = LoginUiState.Failure)
                }
                Timber.tag(TAG).e(throwable, "카카오 로그인 실패")
            }
    }

    companion object {
        private const val TAG = "FCM"
    }
}
