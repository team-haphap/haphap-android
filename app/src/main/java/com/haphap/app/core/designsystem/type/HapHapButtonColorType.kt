package com.haphap.app.core.designsystem.type

sealed interface HapHapButtonColorType {
    data class Primary(val enabled: Boolean = true) : HapHapButtonColorType
    data object Cancel : HapHapButtonColorType
}