package com.haphap.app.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.presentation.setting.SettingContract.SideEffect.NavigateToMyPage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(SettingContract.State())
    val uiState: StateFlow<SettingContract.State> = _uiState.asStateFlow()

    private val _sideEffect = Channel<SettingContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    fun onBackClick() {
        viewModelScope.launch {
            _sideEffect.send(NavigateToMyPage)
        }
    }
}
