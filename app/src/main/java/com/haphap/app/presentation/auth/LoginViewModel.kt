package com.haphap.app.presentation.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.core.state.UiState
import com.haphap.app.data.local.datasource.api.LocalTokenDataSource
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val localTokenDataSource: LocalTokenDataSource,
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<OAuthToken>>(UiState.Idle)
    val loginState: StateFlow<UiState<OAuthToken>> = _loginState.asStateFlow()

    fun onKakaoLoginClick(context: Context) {
        _loginState.value = UiState.Loading

        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                handleKakaoTalkResult(context, token, error)
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = ::handleResult)
        }
    }

    private fun handleKakaoTalkResult(context: Context, token: OAuthToken?, error: Throwable?) {
        if (error != null) {
            if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                _loginState.value = UiState.Idle
                return
            }
            UserApiClient.instance.loginWithKakaoAccount(context, callback = ::handleResult)
        } else if (token != null) {
            saveTokenAndUpdateState(token)
        }
    }

    private fun handleResult(token: OAuthToken?, error: Throwable?) {
        when {
            error is ClientError && error.reason == ClientErrorCause.Cancelled -> {
                _loginState.value = UiState.Idle
            }
            error != null -> {
                _loginState.value = UiState.Failure(error.message ?: "로그인에 실패했습니다.")
            }
            token != null -> {
                saveTokenAndUpdateState(token)
            }
        }
    }

    private fun saveTokenAndUpdateState(token: OAuthToken) {
        viewModelScope.launch {
            localTokenDataSource.setAccessToken(token.accessToken)
            token.refreshToken?.let { localTokenDataSource.setRefreshToken(it) }
            _loginState.value = UiState.Success(token)
        }
    }

    fun consumeFailure() {
        _loginState.value= UiState.Idle
    }
}