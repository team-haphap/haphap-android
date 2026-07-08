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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.detail.JobStepModel
import com.haphap.app.presentation.jobdetail.style.jobStageStepStyle
import com.haphap.app.presentation.jobdetail.type.JobStepStatus
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun JobStageStepRow(
    steps: ImmutableList<JobStepModel>,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.padding(horizontal = 13.dp),
        horizontalArrangement = Arrangement.spacedBy(2.dp),
    ) {
        items(
            items = steps,
            key = { it.stageId }
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
private fun JobStageStep(
    number: Int,
    stageName: String,
    status: JobStepStatus,
    modifier: Modifier = Modifier,
) {
    val style = jobStageStepStyle(status)

    Column(
        modifier = modifier
            .widthIn(min = 46.dp)
            .padding(top = 2.dp, bottom = 3.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = number.toString(),
            style = HapHapTheme.typography.caption.sb12,
            color = style.numberColor,
            modifier = Modifier
                .size(23.dp)
                .background(color = style.circleColor, shape = CircleShape)
                .wrapContentSize(Alignment.Center),
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = stageName,
            style = style.stageNameStyle,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(style.stateSpacerHeight))

        Text(
            text = style.stateText,
            style = style.stateTextStyle,
            color = style.stateTextColor,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JobStageStepRowPreview() {
    HapHapTheme {
        JobStageStepRow(
            steps = persistentListOf(
                JobStepModel(1,1, "서류", JobStepStatus.COMPLETED),
                JobStepModel(2,2, "서류", JobStepStatus.COMPLETED),
                JobStepModel(3,3, "1차면접", JobStepStatus.IN_PROGRESS),
                JobStepModel(4,2, "서류", JobStepStatus.UPCOMING),
                JobStepModel(5,2, "서류", JobStepStatus.UPCOMING),
                JobStepModel(6,2, "서류", JobStepStatus.UPCOMING),
                JobStepModel(7,2, "서류", JobStepStatus.UPCOMING),
            ),
        )
    }
}