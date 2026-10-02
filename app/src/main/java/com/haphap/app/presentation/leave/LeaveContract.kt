package com.haphap.app.presentation.leave

import androidx.compose.runtime.Immutable
import com.haphap.app.presentation.leave.type.LeaveReasonType

sealed interface LeaveContract {
    @Immutable
    data class State(
        val selectedReason: LeaveReasonType? = null,
        val isEtcReasonBlank: Boolean = true,
    ) {
        val isButtonEnabled: Boolean
            get() = when (selectedReason) {
                null -> false
                LeaveReasonType.ETC -> !isEtcReasonBlank
                else -> true
            }
    }
}
