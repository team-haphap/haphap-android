package com.haphap.app.presentation.setting

sealed interface SettingContract {
    data class State(
        val settingUiState: SettingUiState = SettingUiState.Idle,
    )

    sealed interface SideEffect {
        data object NavigateToMyPage : SideEffect
    }
}

sealed interface SettingUiState {
    data object Idle : SettingUiState
    data object Loading : SettingUiState
    data object Success : SettingUiState
    data class Failure(
        val msg: String,
    ) : SettingUiState
}