package com.haphap.app.presentation.myapplication.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.chip.HapHapInfoChip
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.InfoChipType

@Composable
fun MyApplicationCardItem(
    logoImageUrl: String,
    company: String,
    title: String,
    currentStage: String?,
    result: String,
    modifier: Modifier = Modifier,
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = HapHapTheme.colors.gray50)
            .border(
                width = 1.dp,
                color = HapHapTheme.colors.gray100,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(vertical = 14.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = company,
                style = HapHapTheme.typography.body.sb14,
                color = HapHapTheme.colors.gray500,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = title,
                style = HapHapTheme.typography.body.sb14,
                color = HapHapTheme.colors.gray800,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(12.dp))

            MyApplicationInfoRow(
                label = "현재 전형",
                chipText = currentStage ?: "마감",
                chipType = if (currentStage == null) {
                    InfoChipType.CLOSED_DARKNESS
                } else {
                    InfoChipType.EXPECTED
                },
            )

            Spacer(modifier = Modifier.height(6.dp))

            MyApplicationInfoRow(
                label = "내 결과",
                chipText = result,
                chipType = InfoChipType.EXPECTED,
            )
        }

        UrlImage(
            url = logoImageUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = HapHapTheme.colors.white)
                .border(
                    width = 1.dp,
                    color = HapHapTheme.colors.gray100,
                    shape = RoundedCornerShape(10.dp)
                ),
        )
    }
}

@Composable
private fun MyApplicationInfoRow(
    label: String,
    chipText: String,
    chipType: InfoChipType,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(
            text = label,
            style = HapHapTheme.typography.caption.m12,
            color = HapHapTheme.colors.gray500,
            maxLines = 1,
            modifier = Modifier.width(45.dp),
        )

        HapHapInfoChip(
            chipText = chipText,
            type = chipType,
        )
    }
}

@Preview
@Composable
private fun MyApplicationCardItemPreview() {
    HapHapTheme {
        MyApplicationCardItem(
            logoImageUrl = "",
            company = "카카오2026 신입 개발자 공개채용2026 신입 개발자 공개채용2026 신입 개발자 공개채용2026 신입 개발자 공개채용2026 신입 개발자 공개채용",
            title = "2026 신입 개발자 공개채용2026 신입 개발자 공개채용2026 신입 개발자 공개채용2026 신입 개발자 공개채용2026 신입 개발자 공개채용",
            currentStage = "1차 면접",
            result = "1차 면접 아직 몰라요",
            modifier = Modifier.padding(all = 20.dp),
        )
    }
}
