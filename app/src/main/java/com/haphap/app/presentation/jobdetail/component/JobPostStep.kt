package com.haphap.app.presentation.jobdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.jobdetail.type.JobStepStatus
import kotlinx.collections.immutable.ImmutableList


@Composable
fun JobPostStep(
    number: Int,
    stageName: String,
    stateText: String,
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
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
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

        Text(
            text = stageName,
            style = stageNameStyle,
            color = HapHapTheme.colors.gray800,
        )

        Text(
            text = stateText,
            style = stateTextStyle,
            color = stateTextColor,
        )
    }
}

@Composable
fun JobPostStepRow(
    steps: ImmutableList<JobStep>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        items(steps) { step ->
            JobPostStep(
                number = step.number,
                stageName = step.stageName,
                stateText = step.stateText,
                status = step.status,
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JobPostStepPreview() {
    HapHapTheme {
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.padding(16.dp),
        ) {
            JobPostStep(
                number = 1,
                stageName = "서류",
                stateText = "완료",
                status = JobStepStatus.COMPLETED,
            )
            JobPostStep(
                number = 2,
                stageName = "인적성",
                stateText = "진행중",
                status = JobStepStatus.IN_PROGRESS,
            )
            JobPostStep(
                number = 3,
                stageName = "코딩테스트",
                stateText = "대기",
                status = JobStepStatus.UPCOMING,
            )
        }
    }
}