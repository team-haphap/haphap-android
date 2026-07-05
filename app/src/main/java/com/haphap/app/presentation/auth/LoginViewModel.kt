package com.haphap.app.presentation.auth

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.core.state.UiState
import com.haphap.app.data.model.auth.KakaoLoginModel
import com.haphap.app.data.repository.api.auth.AuthRepository
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthError
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
    private val authRepository: AuthRepository,
) : ViewModel() {

    private val _loginState = MutableStateFlow<UiState<KakaoLoginModel>>(UiState.Idle)
    val loginState: StateFlow<UiState<KakaoLoginModel>> = _loginState.asStateFlow()

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
            when {
                isCancelledByUser(error) -> {
                    _loginState.value = UiState.Idle
                }
                isNetworkError(error) -> {
                    _loginState.value = UiState.Failure("네트워크 연결을 확인해 주세요.")
                }
                else -> {
                    UserApiClient.instance.loginWithKakaoAccount(
                        context,
                        callback = ::handleResult,
                    )
                }
            }
        } else if (token != null) {
            requestServerLogin(token)
        }
    }

    private fun handleResult(token: OAuthToken?, error: Throwable?) {
        when {
            isCancelledByUser(error) -> {
                _loginState.value = UiState.Idle
            }
            error != null && isNetworkError(error) -> {
                _loginState.value = UiState.Failure("네트워크 연결을 확인해 주세요.")
            }
            error != null -> {
                _loginState.value = UiState.Failure("잠시 후 다시 시도해 주세요.")
            }
            token != null -> {
                requestServerLogin(token)
            }
        }
    }

    private fun isCancelledByUser(error: Throwable?): Boolean {
        if (error == null) return false
        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) return true
        if (error is com.kakao.sdk.common.model.AuthError) {
            return error.response?.error == "access_denied"
        }
        return false
    }

    private fun isNetworkError(error: Throwable): Boolean {
        if (error is AuthError) {
            val description = error.response?.errorDescription ?: ""
            return description.contains("ERR_INTERNET_DISCONNECTED")
                    || description.contains("ERR_NAME_NOT_RESOLVED")
                    || description.contains("ERR_CONNECTION_REFUSED")
                    || description.contains("ERR_NETWORK_CHANGED")
                    || description.contains("net::")
        }

        return error is java.net.UnknownHostException
                || error is java.net.SocketTimeoutException
                || error is java.io.IOException
                || error.cause is java.net.UnknownHostException
                || error.cause is java.net.SocketTimeoutException
                || error.cause is java.io.IOException
    }

    private fun requestServerLogin(token: OAuthToken) {
        viewModelScope.launch {
            authRepository.postKakaoLogin(token.accessToken)
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
        _loginState.value= UiState.Idle
    }
}