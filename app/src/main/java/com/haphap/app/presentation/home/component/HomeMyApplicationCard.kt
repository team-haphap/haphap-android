package com.haphap.app.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.presentation.calendar.component.CalendarStatusChip

@Composable
fun HomeMyApplicationCard(
    imageUrl: String,
    companyName: String,
    title: String,
    position: String,
    stageName: String,
    dDay: String,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(6.dp))
            .background(HapHapTheme.colors.white)
            .noRippleClickable(onClick = onCardClick)
            .padding(all = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HomeMyApplicationCardImage(
            imageUrl = imageUrl,
            modifier = Modifier
                .height(46.dp)
                .aspectRatio(1f),
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column (modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = companyName,
                    style = HapHapTheme.typography.body.sb14,
                    color = HapHapTheme.colors.gray700,
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = title,
                    style = HapHapTheme.typography.caption.m12,
                    color = HapHapTheme.colors.gray400,
                )
            }

            CalendarStatusChip(
                chipText = stageName,
                isExpectedStage = true,
            )
        }

        Text(
            text = dDay,
            style = HapHapTheme.typography.caption.m10,
            color = HapHapTheme.colors.gray700,
            modifier = Modifier.padding(end = 14.dp),
        )
    }
}

@Composable
private fun HomeMyApplicationCardImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .border(
                color = HapHapTheme.colors.gray100,
                width = 1.dp,
                shape = RoundedCornerShape(8.dp),
            )
    ) {
        UrlImage(
            url = imageUrl,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(shape = RoundedCornerShape(8.dp)),
        )
    }
}

@Preview
@Composable
private fun HomeMyApplicationCardPreview() {
    HapHapTheme {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            HomeMyApplicationCard(
                imageUrl = "",
                companyName = "카카오",
                title = "AI 서비스 기획",
                position = "개발",
                stageName = "1차 면접 발표 중",
                dDay = "D-2",
                onCardClick = {},
            )
        }
    }
}
