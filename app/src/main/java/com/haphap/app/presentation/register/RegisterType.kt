package com.haphap.app.presentation.register

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapColors

enum class DropDownItemSelectionState {
    SELECTED,
    UNSELECTED,
}
data class RegisterDropDownItemStyle(
    val backgroundColor: Color,
    val textColor: Color,
)

fun DropDownItemSelectionState.toStyle(colors: HapHapColors): RegisterDropDownItemStyle = when (this) {
    DropDownItemSelectionState.SELECTED -> RegisterDropDownItemStyle(
        backgroundColor = colors.sub100,
        textColor = colors.primary500,
    )
    DropDownItemSelectionState.UNSELECTED -> RegisterDropDownItemStyle(
        backgroundColor = colors.gray50,
        textColor = colors.gray500,
    )
}

enum class PassResultStatus(val text: String) {
    PASS("합격했어요"),
    FAILED("불합격했어요"),
    DONT_KNOW("아직 몰라요"),
}

enum class PassResultStatusButton(
    val text: String,
    @DrawableRes val defaultBadgeRes: Int,
    @DrawableRes val selectedBadgeRes: Int
) {
    PASS(
        text = "합격했어요",
        defaultBadgeRes = R.drawable.img_register_pass_default,
        selectedBadgeRes = R.drawable.img_register_pass_selected
    ),
    FAILED(
        text = "불합격했어요",
        defaultBadgeRes = R.drawable.img_register_fail_default,
        selectedBadgeRes = R.drawable.img_register_fail_selected
    ),
    DONT_KNOW(
        text = "아직 몰라요",
        defaultBadgeRes = R.drawable.img_register_wait_default,
        selectedBadgeRes = R.drawable.img_register_wait_selected
    )
}