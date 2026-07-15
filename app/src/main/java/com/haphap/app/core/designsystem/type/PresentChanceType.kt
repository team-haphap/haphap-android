package com.haphap.app.core.designsystem.type

import androidx.compose.ui.graphics.Color
import com.haphap.app.core.designsystem.theme.HapHapColors

enum class PresentChanceType {
    NONE,
    VERY_LOW,
    LOW,
    MEDIUM,
    HIGH,
    VERY_HIGH,
}

fun PresentChanceType.toColor(colors: HapHapColors): Color = when (this) {
    PresentChanceType.NONE -> colors.gray400
    PresentChanceType.VERY_LOW -> colors.sub300
    PresentChanceType.LOW -> colors.sub200
    PresentChanceType.MEDIUM -> colors.primary100
    PresentChanceType.HIGH -> colors.primary500
    PresentChanceType.VERY_HIGH -> colors.sub400
}