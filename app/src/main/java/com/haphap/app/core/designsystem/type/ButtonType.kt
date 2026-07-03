package com.haphap.app.core.designsystem.type

sealed interface ButtonType {
    data class Primary(val enabled: Boolean = true) : ButtonType
    data object Cancel : ButtonType
}