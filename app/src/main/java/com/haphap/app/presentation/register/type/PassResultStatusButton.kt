package com.haphap.app.presentation.register.type

import androidx.annotation.DrawableRes
import com.haphap.app.R
import com.haphap.app.data.model.register.RegisterResultType

enum class PassResultStatusButton(
    val text: String,
    @DrawableRes val defaultBadgeRes: Int,
    @DrawableRes val selectedBadgeRes: Int,
) {
    PASS(
        text = "합격했어요",
        defaultBadgeRes = R.drawable.img_register_pass_default,
        selectedBadgeRes = R.drawable.img_register_pass_selected,
    ),
    FAILED(
        text = "불합격했어요",
        defaultBadgeRes = R.drawable.img_register_fail_default,
        selectedBadgeRes = R.drawable.img_register_fail_selected,
    ),
    DONT_KNOW(
        text = "아직 몰라요",
        defaultBadgeRes = R.drawable.img_register_wait_default,
        selectedBadgeRes = R.drawable.img_register_wait_selected,
    ),
}

fun PassResultStatusButton.toResultType(): RegisterResultType = when (this) {
    PassResultStatusButton.PASS -> RegisterResultType.PASS
    PassResultStatusButton.FAILED -> RegisterResultType.FAIL
    PassResultStatusButton.DONT_KNOW -> RegisterResultType.PENDING
}
