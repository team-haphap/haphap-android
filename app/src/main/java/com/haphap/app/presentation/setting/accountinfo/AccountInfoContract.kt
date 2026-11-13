package com.haphap.app.presentation.setting.accountinfo

import androidx.compose.runtime.Immutable

sealed interface AccountInfoContract {
    @Immutable
    data class State(
        val loginProvider: String = "",
        val name: String = "",
        val email: String = "",
    )
}
