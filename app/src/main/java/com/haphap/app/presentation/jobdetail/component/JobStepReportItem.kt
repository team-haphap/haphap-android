package com.haphap.app.presentation.jobdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.chip.HapHapStatusChip
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.StatusChipType
import com.haphap.app.presentation.jobdetail.type.JobStepReportType

@Composable
fun JobStepReportItem(
    time: String,
    nickName: String,
    result: JobStepReportType,
    stage: String,
    modifier: Modifier = Modifier,
) {
    val content = when (result) {
        JobStepReportType.PASS -> "${nickName}이 합격 결과를 공유했어요!"
        JobStepReportType.FAIL -> "${nickName}이 불합격 결과를 공유했어요"
        JobStepReportType.PENDING -> "${nickName}이 대기 상태를 공유했어요"
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(color = HapHapTheme.colors.white)
    ) {
     Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = time,
                style = HapHapTheme.typography.caption.r11,
                color = HapHapTheme.colors.gray600,
            )

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f),
            ) {
                Text(
                    text = "새 제보 등록!",
                    style = HapHapTheme.typography.body.sb14,
                    color = HapHapTheme.colors.gray800,
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = content,
                    style = HapHapTheme.typography.caption.r11,
                    color = HapHapTheme.colors.gray600,
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            HapHapStatusChip(
                text = stage,
                type = StatusChipType.STAGE,
            )
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = HapHapTheme.colors.gray100
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun JobStepReportItemPreview() {
    HapHapTheme {
        Column {
            JobStepReportItem(
                time = "12:21",
                nickName = "익명의 라이언",
                result = JobStepReportType.PASS,
                stage = "서류",
            )
            JobStepReportItem(
                time = "12:21",
                nickName = "익명의 라이언",
                result = JobStepReportType.FAIL,
                stage = "서류",
            )
            JobStepReportItem(
                time = "12:21",
                nickName = "익명의 라이언",
                result = JobStepReportType.PENDING,
                stage = "서류",
            )
        }
    }
}