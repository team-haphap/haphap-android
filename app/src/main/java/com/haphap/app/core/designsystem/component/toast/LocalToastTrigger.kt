package com.haphap.app.core.designsystem.component.toast

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val LocalToastTrigger = staticCompositionLocalOf<(message: String, isAlarm: Boolean) -> Unit> {
    error("No Toast provided")
}

val LocalToastBottomInset = staticCompositionLocalOf<MutableState<Dp>> {
    mutableStateOf(0.dp)
}
