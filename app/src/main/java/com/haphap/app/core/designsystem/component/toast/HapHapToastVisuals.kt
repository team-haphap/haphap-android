package com.haphap.app.core.designsystem.component.toast

import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarVisuals

data class HapHapToastVisuals(
    override val message: String,
    val isAlarm: Boolean = true,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Indefinite,
) : SnackbarVisuals
