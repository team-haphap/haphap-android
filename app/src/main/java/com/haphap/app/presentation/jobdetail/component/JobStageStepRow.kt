package com.haphap.app.presentation.jobdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
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
import com.haphap.app.data.model.detail.JobStepModel
import com.haphap.app.presentation.jobdetail.type.JobStepStatus
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun JobStageStepRow(
    steps: ImmutableList<JobStepModel>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items(
            items = steps,
            key = { it }
        ) { step ->
            JobStageStep(
                number = step.number,
                stageName = step.stageName,
                status = step.status,
            )
        }
    }
}

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
            .padding(top = 2.dp, bottom = 3.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = number.toString(),
            style = HapHapTheme.typography.caption.sb12,
            color = numberColor,
            modifier = Modifier
                .size(23.dp)
                .background(color = circleColor, shape = CircleShape)
                .wrapContentSize(Alignment.Center)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stageName,
            style = stageNameStyle,
            color = stageNameColor,
        )

        if (isActive)
            Spacer(modifier = Modifier.height(3.dp))
        else
            Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = stateText,
            style = stateTextStyle,
            color = stateTextColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JobStageStepRowPreview() {
    HapHapTheme {
        JobStageStepRow(
            steps = persistentListOf(
                JobStepModel(1, "서류", JobStepStatus.COMPLETED),
                JobStepModel(2, "서류", JobStepStatus.COMPLETED),
                JobStepModel(3, "1차면접", JobStepStatus.IN_PROGRESS),
                JobStepModel(2, "서류", JobStepStatus.UPCOMING),
                JobStepModel(2, "서류", JobStepStatus.UPCOMING),
                JobStepModel(2, "서류", JobStepStatus.UPCOMING),
                JobStepModel(2, "서류", JobStepStatus.UPCOMING),
            ),
        )
    }
}