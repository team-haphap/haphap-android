package com.haphap.app.presentation.passcertification.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun PassCertificationInfoSection(
    companyName: String,
    title: String,
    stageName: String,
    submittedDate: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = companyName,
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray400,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = title,
            style = HapHapTheme.typography.title.b26,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(12.dp))

        PassCertificationInfoRow(label = "전형") {
            PassCertificationInfoValue(text = stageName)
        }

        PassCertificationInfoRow(label = "등록한 결과") {
            PassCertificationInfoValue(text = "합격")
        }

        PassCertificationInfoRow(label = "인증 상태") {
            PassCertificationRejectChip(text = "반려")
        }

        PassCertificationInfoRow(
            label = "제출일",
            hasDivider = false,
        ) {
            PassCertificationInfoValue(text = submittedDate)
        }
    }
}

@Composable
private fun PassCertificationInfoRow(
    label: String,
    modifier: Modifier = Modifier,
    hasDivider: Boolean = true,
    content: @Composable () -> Unit,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = label,
                style = HapHapTheme.typography.body.b14,
                color = HapHapTheme.colors.gray400,
                modifier = Modifier.width(76.dp),
            )

            content()
        }

        if (hasDivider) {
            HorizontalDivider(
                thickness = 1.dp,
                color = HapHapTheme.colors.gray100,
            )
        }
    }
}

@Composable
private fun PassCertificationInfoValue(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = HapHapTheme.typography.body.sb14,
        color = HapHapTheme.colors.gray700,
        modifier = modifier,
    )
}

@Composable
private fun PassCertificationRejectChip(
    text: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = text,
        style = HapHapTheme.typography.caption.sb10,
        color = HapHapTheme.colors.subred,
        modifier = modifier
            .clip(shape = CircleShape)
            .background(color = HapHapTheme.colors.subred2)
            .padding(horizontal = 6.dp, vertical = 2.dp),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PassCertificationInfoSectionPreview() {
    HapHapTheme {
        PassCertificationInfoSection(
            companyName = "가온테크",
            title = "서비스 기획 인턴",
            stageName = "서류",
            submittedDate = "2026.10.15."
        )
    }
}
