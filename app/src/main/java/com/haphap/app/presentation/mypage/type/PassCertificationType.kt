package com.haphap.app.presentation.mypage.type

import androidx.annotation.DrawableRes
import com.haphap.app.R

enum class PassCertificationType(
    @DrawableRes val iconRes: Int,
    val activeTitle: (Int) -> String,
    val emptyTitle: String,
    val activeDescription: String,
    val emptyDescription: String,
    val buttonText: String,
    val disabledToastMessage: String,
) {
    REJECTED(
        iconRes = R.drawable.ic_alert_15,
        activeTitle = { count -> "합격 인증 ${count}건이\n반려됐어요." },
        emptyTitle = "반려된 합격 인증\n내역이 아직 없어요.",
        activeDescription = "다시 제출해주세요",
        emptyDescription = "반려 내역이 아직 없어요",
        buttonText = "제출하러 가기",
        disabledToastMessage = "반려된 합격 인증 내역이 없어요.",
    ),
    APPROVED(
        iconRes = R.drawable.ic_check_15,
        activeTitle = { count -> "합격 인증 ${count}건이\n승인됐어요" },
        emptyTitle = "합격 인증이\n아직 없어요.",
        activeDescription = "다시 제출해주세요",
        emptyDescription = "합격카드가 아직 없어요",
        buttonText = "합격카드 보러가기",
        disabledToastMessage = "합격 인증 내역이 없어요.",
    ),
}
