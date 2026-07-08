package com.haphap.app.presentation.calendar.type

import androidx.compose.ui.graphics.Color
import com.haphap.app.core.designsystem.theme.HapHapColors

enum class PresentChance {
    NONE,
    VERY_LOW,
    LOW,
    MEDIUM,
    HIGH,
    VERY_HIGH,
}

fun PresentChance.toColor(colors: HapHapColors): Color = when (this) {
    PresentChance.NONE -> colors.gray400
    PresentChance.VERY_LOW -> colors.sub300
    PresentChance.LOW -> colors.sub200
    PresentChance.MEDIUM -> colors.primary100
    PresentChance.HIGH -> colors.primary500
    PresentChance.VERY_HIGH -> colors.sub400
}