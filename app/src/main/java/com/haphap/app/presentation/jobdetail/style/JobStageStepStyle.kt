package com.haphap.app.presentation.jobdetail.style

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.jobdetail.type.JobStepStatus

data class JobStageStepStyle(
    val circleColor: Color,
    val numberColor: Color,
    val stageNameStyle: TextStyle,
    val stateText: String,
    val stateSpacerHeight: Dp,
    val stateTextColor: Color,
    val stateTextStyle: TextStyle,
)

@Composable
fun jobStageStepStyle(status: JobStepStatus): JobStageStepStyle {
    val isActive = status != JobStepStatus.UPCOMING

    return JobStageStepStyle(
        circleColor = if (isActive) HapHapTheme.colors.primary500 else HapHapTheme.colors.gray200,
        numberColor = if (isActive) HapHapTheme.colors.gray100 else HapHapTheme.colors.gray500,
        stageNameStyle = if (isActive) {
            HapHapTheme.typography.caption.sb10
        } else {
            HapHapTheme.typography.caption.r10
        },
        stateText = when (status) {
            JobStepStatus.COMPLETED -> "완료"
            JobStepStatus.IN_PROGRESS -> "진행중"
            JobStepStatus.UPCOMING -> "대기"
        },
        stateSpacerHeight = if (isActive) 3.dp else 2.dp,
        stateTextColor = if (isActive) HapHapTheme.colors.primary100 else HapHapTheme.colors.gray300,
        stateTextStyle = if (isActive) {
            HapHapTheme.typography.caption.m10
        } else {
            HapHapTheme.typography.caption.r10
        },
    )
}