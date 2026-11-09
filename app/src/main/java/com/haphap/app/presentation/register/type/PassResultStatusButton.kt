package com.haphap.app.presentation.register.type

import androidx.annotation.DrawableRes
import com.haphap.app.R
import com.haphap.app.data.model.register.RegisterResultType

enum class PassResultStatusButton(
    val text: String,
    @DrawableRes val defaultBadgeRes: Int,
    @DrawableRes val selectedBadgeRes: Int,
    val resultType: RegisterResultType,
) {
    PASS(
        text = "합격했어요",
        defaultBadgeRes = R.drawable.img_register_pass_default,
        selectedBadgeRes = R.drawable.img_register_pass_selected,
        resultType = RegisterResultType.PASS,
    ),
    FAILED(
        text = "불합격했어요",
        defaultBadgeRes = R.drawable.img_register_fail_default,
        selectedBadgeRes = R.drawable.img_register_fail_selected,
        resultType = RegisterResultType.FAIL,
    ),
    DONT_KNOW(
        text = "아직 몰라요",
        defaultBadgeRes = R.drawable.img_register_wait_default,
        selectedBadgeRes = R.drawable.img_register_wait_selected,
        resultType = RegisterResultType.PENDING,
    )
}
