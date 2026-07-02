package com.haphap.app.core.designsystem.type

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class HapHapCardType(
    val imageRatio: Float,
    val cardRadius: Dp,
    val imageToChipHeight: Dp,
) {
    BIG(
        imageRatio = 170f / 82f,
        cardRadius = 8.dp,
        imageToChipHeight = 12.dp,
    ),
    SMALL(
        imageRatio = 131f / 82f,
        cardRadius = 12.dp,
        imageToChipHeight = 6.dp,
    )
}