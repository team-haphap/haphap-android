package com.haphap.app.presentation.register.type

import androidx.compose.ui.graphics.Color
import com.haphap.app.core.designsystem.theme.HapHapColors

enum class RegisterDropDownItemType {
    SELECTED,
    UNSELECTED,
}
data class RegisterDropDownItemStyle(
    val backgroundColor: Color,
    val textColor: Color,
)

fun RegisterDropDownItemType.toStyle(colors: HapHapColors): RegisterDropDownItemStyle = when (this) {
    RegisterDropDownItemType.SELECTED -> RegisterDropDownItemStyle(
        backgroundColor = colors.sub100,
        textColor = colors.primary500,
    )
    RegisterDropDownItemType.UNSELECTED -> RegisterDropDownItemStyle(
        backgroundColor = colors.gray50,
        textColor = colors.gray500,
    )
}