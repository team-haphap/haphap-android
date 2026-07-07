package com.haphap.app.core.designsystem.type

import android.widget.Button

sealed interface ButtonType {
    data class Primary(val enabled: Boolean = true) : ButtonType
    data object Cancel : ButtonType
    data object Selected : ButtonType
    data object Default : ButtonType
}