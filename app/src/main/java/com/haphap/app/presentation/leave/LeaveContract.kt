package com.haphap.app.presentation.leave

import androidx.compose.runtime.Immutable
import com.haphap.app.presentation.leave.type.LeaveReasonType

sealed interface LeaveContract {
    @Immutable
    data class State(
        val selectedReason: LeaveReasonType? = null,
        val isEtcReasonBlank: Boolean = true,
        val isLoading: Boolean = false,
    ) {
        val isButtonEnabled: Boolean
            get() = !isLoading && when (selectedReason) {
                null -> false
                LeaveReasonType.ETC -> !isEtcReasonBlank
                else -> true
            }
    }

    sealed interface SideEffect {
        data object NavigateToSetting : SideEffect
        data object NavigateToLeaveComplete : SideEffect
    }
}