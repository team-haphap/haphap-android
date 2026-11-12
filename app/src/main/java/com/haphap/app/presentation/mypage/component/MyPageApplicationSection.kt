package com.haphap.app.presentation.mypage.component

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.chip.HapHapInfoChip
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.InfoChipType
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.data.model.mypage.MyRecentApplicationModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MyPageApplicationSection(
    applicationList: ImmutableList<MyRecentApplicationModel>,
    onApplicationClick: (Int) -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = "내 지원 내역",
                style = HapHapTheme.typography.body.sb14,
                color = HapHapTheme.colors.gray700,
                modifier = Modifier.weight(1f),
            )

            Text(
                text = "전체보기",
                style = HapHapTheme.typography.caption.m12,
                color = HapHapTheme.colors.gray400,
                modifier = Modifier.noRippleClickable(onClick = onMoreClick),
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            applicationList.forEach { application ->
                MyPageApplicationCard(
                    application = application,
                    onCardClick = { onApplicationClick(application.id) },
                )
            }
        }

        if (applicationList.size >= 2) {
            Spacer(modifier = Modifier.height(12.dp))

            MyPageScrollIndicator(
                scrollState = scrollState,
                itemCount = applicationList.size,
                modifier = Modifier.padding(horizontal = 30.dp),
            )
        }
    }
}

@Composable
private fun MyPageApplicationCard(
    application: MyRecentApplicationModel,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .height(88.dp)
            .aspectRatio(289f / 88f)
            .background(
                color = HapHapTheme.colors.gray50,
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 1.dp,
                color = HapHapTheme.colors.gray100,
                shape = RoundedCornerShape(8.dp),
            )
            .noRippleClickable(onClick = onCardClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
    ) {
        Column(
            modifier = Modifier.weight(1f),
        ) {
            Text(
                text = application.title,
                style = HapHapTheme.typography.body.sb14,
                color = HapHapTheme.colors.gray800,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
            ) {
                HapHapInfoChip(
                    chipText = application.stageName,
                    type = InfoChipType.EXPECTED,
                )

                HapHapInfoChip(
                    chipText = "${application.participantCount}명 참여중",
                    type = InfoChipType.COUNT,
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "${application.appliedDate} 지원",
                style = HapHapTheme.typography.caption.r10,
                color = HapHapTheme.colors.gray500,
            )
        }

        UrlImage(
            url = application.logoImageUrl,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    width = 1.dp,
                    color = HapHapTheme.colors.gray100,
                    shape = RoundedCornerShape(10.dp),
                ),
        )
    }
}

@Composable
private fun MyPageScrollIndicator(
    scrollState: ScrollState,
    itemCount: Int,
    modifier: Modifier = Modifier,
) {
    val thumbColor = HapHapTheme.colors.gray700

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 7.dp)
            .height(2.dp)
            .clip(CircleShape)
            .background(HapHapTheme.colors.gray200)
            .drawBehind {
                val thumbWidth = size.width / itemCount
                val progress = if (scrollState.maxValue > 0) {
                    scrollState.value.toFloat() / scrollState.maxValue
                } else {
                    0f
                }

                drawRoundRect(
                    color = thumbColor,
                    topLeft = Offset(x = (size.width - thumbWidth) * progress, y = 0f),
                    size = Size(width = thumbWidth, height = size.height),
                    cornerRadius = CornerRadius(size.height / 2),
                )
            },
    )
}

@Preview(showBackground = true)
@Composable
private fun MyPageApplicationSectionPreview() {
    HapHapTheme {
        MyPageApplicationSection(
            applicationList = persistentListOf(
                MyRecentApplicationModel(
                    id = 1,
                    title = "2026 신입 개발자 공개채용",
                    stageName = "서류 발표",
                    participantCount = 12,
                    appliedDate = "2024.02.09",
                    logoImageUrl = "",
                ),
                MyRecentApplicationModel(
                    id = 2,
                    title = "2026 상반기 신입 공채",
                    stageName = "1차 면접",
                    participantCount = 30,
                    appliedDate = "2024.02.12",
                    logoImageUrl = "",
                ),
            ),
            onApplicationClick = {},
            onMoreClick = {},
            modifier = Modifier.padding(vertical = 16.dp),
        )
    }
}
