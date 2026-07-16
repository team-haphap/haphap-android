package com.haphap.app.presentation.auth.login

import androidx.compose.runtime.Immutable

sealed interface LoginContract {
    @Immutable
    data class State(
        val loginUiState: LoginUiState = LoginUiState.Idle,
    )

    sealed class SideEffect {
        data class NavigateToSignUpComplete(val userName: String) : SideEffect()
    }
}

sealed interface LoginUiState {
    data object Idle : LoginUiState
    data object Loading : LoginUiState
    data object Success : LoginUiState
    data object Failure : LoginUiState
}