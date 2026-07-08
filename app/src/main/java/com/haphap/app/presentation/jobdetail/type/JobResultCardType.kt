package com.haphap.app.presentation.jobdetail.type

import androidx.annotation.DrawableRes
import com.haphap.app.R

enum class JobResultCardType(
    @DrawableRes val imageRes: Int,
    val label: String,
) {
    PASS(
        imageRes = R.drawable.img_jd_1,
        label = "합격했어요",
    ),
    FAIL(
        imageRes = R.drawable.img_jd_2,
        label = "불합격했어요",
    ),
    PENDING(
        imageRes = R.drawable.img_jd_3,
        label = "아직 몰라요",
    ),
}
