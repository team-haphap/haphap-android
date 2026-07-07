package com.haphap.app.presentation.jobdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun JobDetailTitleSection(
    companyName: String,
    jobTitle: String,
    keywords: ImmutableList<String>,
    statusText: String,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = companyName,
            style = HapHapTheme.typography.body.b14,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = jobTitle,
            style = HapHapTheme.typography.subtitle.b24,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            keywords.forEach { keyword ->
                Text(
                    text = keyword,
                    style = HapHapTheme.typography.body.sb13,
                    color = HapHapTheme.colors.gray400,
                )
            }
            Text(
                text = statusText,
                style = HapHapTheme.typography.caption.sb12,
                color = HapHapTheme.colors.gray100,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(4.dp))
                    .background(color = HapHapTheme.colors.primary500)
                    .padding(horizontal = 4.dp, vertical = 2.dp),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JobDetailTitleSectionPreview() {
    HapHapTheme {
        JobDetailTitleSection(
            companyName = "카카오 에너지",
            jobTitle = "태양광 사업 정책기획 태양광 사업 정책기획 태양광 사업 정책기획 정책기획 태양광 사업 정책기획",
            keywords = persistentListOf("사업/기획", "양재 본사", "UXUI"),
            statusText = "1차 면접 진행 중",
        )
    }
}