package com.haphap.app.core.designsystem.component.toast

import androidx.compose.runtime.staticCompositionLocalOf

val LocalToastTrigger = staticCompositionLocalOf<(message: String, isAlarm: Boolean) -> Unit> {
    error("No Toast provided")
}
