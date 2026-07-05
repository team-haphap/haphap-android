package com.haphap.app.presentation.auth

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthError
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient

class KakaoLoginManager {

    fun login(
        context: Context,
        onSuccess: (kakaoAccessToken: String) -> Unit,
        onCancel: () -> Unit,
        onNetworkError: () -> Unit,
        onFailure: () -> Unit,
    ) {
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                handleKakaoTalkResult(context, token, error, onSuccess, onCancel, onNetworkError, onFailure)
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context) { token, error ->
                handleResult(token, error, onSuccess, onCancel, onNetworkError, onFailure)
            }
        }
    }

    private fun handleKakaoTalkResult(
        context: Context,
        token: OAuthToken?,
        error: Throwable?,
        onSuccess: (String) -> Unit,
        onCancel: () -> Unit,
        onNetworkError: () -> Unit,
        onFailure: () -> Unit,
    ) {
        if (error != null) {
            when {
                isCancelledByUser(error) -> onCancel()
                isNetworkError(error) -> onNetworkError()
                else -> {
                    UserApiClient.instance.loginWithKakaoAccount(context) { retryToken, retryError ->
                        handleResult(retryToken, retryError, onSuccess, onCancel, onNetworkError, onFailure)
                    }
                }
            }
        } else if (token != null) {
            onSuccess(token.accessToken)
        }
    }

    private fun handleResult(
        token: OAuthToken?,
        error: Throwable?,
        onSuccess: (String) -> Unit,
        onCancel: () -> Unit,
        onNetworkError: () -> Unit,
        onFailure: () -> Unit,
    ) {
        when {
            isCancelledByUser(error) -> onCancel()
            error != null && isNetworkError(error) -> onNetworkError()
            error != null -> onFailure()
            token != null -> onSuccess(token.accessToken)
        }
    }

    private fun isCancelledByUser(error: Throwable?): Boolean {
        if (error == null) return false
        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) return true
        if (error is AuthError) {
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
}