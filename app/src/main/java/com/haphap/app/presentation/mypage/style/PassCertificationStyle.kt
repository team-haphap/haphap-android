package com.haphap.app.presentation.mypage.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.mypage.type.PassCertificationType

data class PassCertificationStyle(
    val pointColor: Color,
    val backgroundColor: Color,
    val activeButtonColor: Color,
)

@Composable
fun passCertificationStyle(type: PassCertificationType): PassCertificationStyle =
    when (type) {
        PassCertificationType.REJECTED -> PassCertificationStyle(
            pointColor = HapHapTheme.colors.subred,
            backgroundColor = HapHapTheme.colors.subred.copy(alpha = 0.12f),
            activeButtonColor = HapHapTheme.colors.subred,
        )

        PassCertificationType.APPROVED -> PassCertificationStyle(
            pointColor = HapHapTheme.colors.primary500,
            backgroundColor = HapHapTheme.colors.sub100,
            activeButtonColor = HapHapTheme.colors.primary100,
        )
    }
