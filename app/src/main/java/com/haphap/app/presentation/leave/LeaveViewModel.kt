package com.haphap.app.presentation.leave

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.mypage.MyPageRepository
import com.haphap.app.presentation.leave.LeaveContract.SideEffect.NavigateToLeaveComplete
import com.haphap.app.presentation.leave.LeaveContract.SideEffect.NavigateToSetting
import com.haphap.app.presentation.leave.type.LeaveReasonType
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
class LeaveViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(LeaveContract.State())
    val uiState: StateFlow<LeaveContract.State> = _uiState.asStateFlow()

    private val _sideEffect = Channel<LeaveContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    val etcReasonState = TextFieldState()

    init {
        observeEtcReason()
    }

    private fun observeEtcReason() = viewModelScope.launch {
        snapshotFlow { etcReasonState.text.isBlank() }
            .collect { isBlank ->
                _uiState.update { it.copy(isEtcReasonBlank = isBlank) }
            }
    }

    fun onBackClick() {
        viewModelScope.launch {
            _sideEffect.send(NavigateToSetting)
        }
    }

    fun onReasonClick(reason: LeaveReasonType) {
        _uiState.update { state ->
            state.copy(selectedReason = if (state.selectedReason == reason) null else reason)
        }
    }

    fun onLeaveClick() {
        val state = _uiState.value
        if (!state.isButtonEnabled) return

        val reason = state.selectedReason ?: return

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            myPageRepository.deleteMember(
                reason = reason.serverValue,
                etcReason = if (reason == LeaveReasonType.ETC) {
                    etcReasonState.text.toString().trim()
                } else {
                    null
                },
            ).onSuccess { _sideEffect.send(NavigateToLeaveComplete) }
                .onFailure { _uiState.update { it.copy(isLoading = false) } }
        }
    }
}
