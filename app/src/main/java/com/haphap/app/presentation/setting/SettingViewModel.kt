package com.haphap.app.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.mypage.MyPageRepository
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
    private val myPageRepository: MyPageRepository,
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
        if (_uiState.value.settingUiState is SettingUiState.Loading) return
        _uiState.update { it.copy(isLogoutDialogVisible = true) }
    }

    fun onLogoutDialogDismiss() {
        _uiState.update { it.copy(isLogoutDialogVisible = false) }
    }

    fun onLogoutConfirm() {
        if (_uiState.value.settingUiState is SettingUiState.Loading) return
        _uiState.update {
            it.copy(
                isLogoutDialogVisible = false,
                settingUiState = SettingUiState.Loading,
            )
        }

        viewModelScope.launch {
            myPageRepository.postLogout()
                .onSuccess { _sideEffect.send(NavigateToLogin) }
                .onFailure {
                    _uiState.update {
                        it.copy(settingUiState = SettingUiState.Idle)
                    }
                }
        }
    }

    fun onLeaveClick() {
        viewModelScope.launch {
            _sideEffect.send(NavigateToLeave)
        }
    }
}
