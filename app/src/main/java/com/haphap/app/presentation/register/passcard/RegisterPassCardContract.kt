package com.haphap.app.presentation.register.passcard

import androidx.compose.runtime.Immutable

sealed interface RegisterPassCardContract {
    @Immutable
    data class State(
        val userName: String = "",
        val recruitName: String = "",
        val companyName: String = "",
        val logoUrl: String = "",
        val backgroundImageUrl: String = "",
    )

    sealed class SideEffect {
        data class OnShowToast (
            val message: String,
            val isAlarm: Boolean = false,
        ): SideEffect()

        data object NavigateToHome : SideEffect()
    }
}
