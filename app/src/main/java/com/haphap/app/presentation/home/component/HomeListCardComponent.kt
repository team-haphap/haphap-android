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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.chip.HapHapStatusChip
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.StatusChipType
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.R

@Composable
fun HomeListCardComponent(
    imageUrl: String,
    title: String,
    companyName: String,
    category: String,
    stageName: String,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(HapHapTheme.colors.gray100)
            .noRippleClickable(onClick = onCardClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        HomeListCardImage(
            imageUrl = imageUrl,
            modifier = Modifier
                .height(54.dp)
                .aspectRatio(1f),
        )

        Spacer(modifier = Modifier.width(6.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = companyName,
                    style = HapHapTheme.typography.caption.m12,
                    color = HapHapTheme.colors.gray700,
                )

                Spacer(modifier = Modifier.width(2.dp))

                HapHapStatusChip(
                    text = category,
                    type = StatusChipType.CATEGORY,
                )
            }

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = title,
                style = HapHapTheme.typography.body.sb14,
                color = HapHapTheme.colors.gray700,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Text(
                text = "[$stageName] 발표 예상",
                style = HapHapTheme.typography.caption.r10,
                color = HapHapTheme.colors.gray600,
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right_24),
            contentDescription = null,
            tint = HapHapTheme.colors.gray500,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Composable
private fun HomeListCardImage(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(shape = RoundedCornerShape(6.dp))
            .background(HapHapTheme.colors.white)
            .border(
                color = HapHapTheme.colors.gray100,
                width = 1.dp,
            )
    ) {
        UrlImage(
            url = imageUrl,
            placeholderDrawable = R.drawable.img_calendar_kakao,
            contentDescription = null,
            modifier = Modifier.matchParentSize(),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeListCardComponentPreview() {
    HapHapTheme {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
        ) {
            HomeListCardComponent(
                imageUrl = "",
                title = "2026 신입 공개채용",
                companyName = "카카오",
                category = "개발",
                stageName = "전형",
                onCardClick = {},
            )
        }
    }
}
