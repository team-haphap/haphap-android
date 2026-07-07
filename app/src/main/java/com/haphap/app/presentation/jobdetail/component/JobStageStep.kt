package com.haphap.app.presentation.jobdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.jobdetail.model.JobStep
import com.haphap.app.presentation.jobdetail.type.JobStepStatus
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun JobStageStep(
    number: Int,
    stageName: String,
    status: JobStepStatus,
    modifier: Modifier = Modifier,
) {
    val isActive = status != JobStepStatus.UPCOMING

    val circleColor = if (isActive) {
        HapHapTheme.colors.primary500
    } else {
        HapHapTheme.colors.gray200
    }
    val numberColor = if (isActive) {
        HapHapTheme.colors.gray100
    } else {
        HapHapTheme.colors.gray500
    }
    val stageNameStyle: TextStyle = if (isActive) {
        HapHapTheme.typography.caption.sb10
    } else {
        HapHapTheme.typography.caption.r10
    }
    val stageNameColor = HapHapTheme.colors.gray800
    val stateText = when (status) {
        JobStepStatus.COMPLETED -> "완료"
        JobStepStatus.IN_PROGRESS -> "진행중"
        JobStepStatus.UPCOMING -> "대기"
    }
    val stateTextColor = if (isActive) {
        HapHapTheme.colors.primary100
    } else {
        HapHapTheme.colors.gray300
    }
    val stateTextStyle: TextStyle = if (isActive) {
        HapHapTheme.typography.caption.m10
    } else {
        HapHapTheme.typography.caption.r10
    }

    Column(
        modifier = modifier
            .widthIn(min = 46.dp)
            .padding(vertical = 2.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .size(23.dp)
                .background(color = circleColor, shape = CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = number.toString(),
                style = HapHapTheme.typography.caption.sb12,
                color = numberColor,
            )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = stageName,
            style = stageNameStyle,
            color = stageNameColor,
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = stateText,
            style = stateTextStyle,
            color = stateTextColor,
        )
    }
}

@Composable
fun JobStageStepRow(
    steps: ImmutableList<JobStep>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items(steps) { step ->
            JobStageStep(
                number = step.number,
                stageName = step.stageName,
                status = step.status,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JobStageStepRowPreview() {
    HapHapTheme {
        JobStageStepRow(
            steps = persistentListOf(
                JobStep(1, "서류", JobStepStatus.COMPLETED),
                JobStep(2, "서류", JobStepStatus.COMPLETED),
                JobStep(3, "1차면접", JobStepStatus.IN_PROGRESS),
                JobStep(2, "서류", JobStepStatus.UPCOMING),
                JobStep(2, "서류", JobStepStatus.UPCOMING),
                JobStep(2, "서류", JobStepStatus.UPCOMING),
                JobStep(2, "서류", JobStepStatus.UPCOMING),
            ),
        )
    }
}