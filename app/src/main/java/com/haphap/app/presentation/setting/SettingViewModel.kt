package com.haphap.app.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.auth.AuthRepository
import com.haphap.app.presentation.setting.SettingContract.SideEffect.NavigateToLeave
import com.haphap.app.presentation.setting.SettingContract.SideEffect.NavigateToMyPage
import com.haphap.app.presentation.setting.SettingContract.SideEffect.NavigateToLogin
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SettingContract.State())
    val uiState: StateFlow<SettingContract.State> = _uiState.asStateFlow()

    private val _sideEffect = Channel<SettingContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onBackClick() {
        viewModelScope.launch {
            _sideEffect.send(NavigateToMyPage)
        }
    }

    fun onLogoutClick() {
        _uiState.update { it.copy(isLogoutDialogVisible = true) }
    }

    fun onLogoutDialogDismiss() {
        _uiState.update { it.copy(isLogoutDialogVisible = false) }
    }

    fun onLogoutConfirm() {
        _uiState.update {
            it.copy(
                isLogoutDialogVisible = false,
                settingUiState = SettingUiState.Loading,
            )
        }
        viewModelScope.launch {
            authRepository.postLogout()
            _sideEffect.send(NavigateToLogin)
        }
    }

    fun onLeaveClick() {
        viewModelScope.launch {
            _sideEffect.send(NavigateToLeave)
        }
    }
}
