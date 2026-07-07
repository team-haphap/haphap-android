package com.haphap.app.core.designsystem.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.chip.HapHapDeadlineChip
import com.haphap.app.core.designsystem.component.chip.HapHapStatusChip
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.CardType
import com.haphap.app.core.designsystem.type.StatusChipType
import com.haphap.app.core.extensions.noRippleClickable

/**
 * 공고 카드 공통 컴포넌트입니다.
 * BIG/SMALL 타입에 따라 이미지 비율, 카드 크기, 텍스트 스타일이 변경됩니다.
 *
 * @param type 카드 타입 (BIG, SMALL)
 * @param imageUrl 카드 이미지 URL
 * @param text 표시할 텍스트 (직무)
 * @param stage 전형명 (예: 서류, 최종)
 * @param dDay 발표까지 남은 일수
 * @param company 회사명
 * @param description 기업 설명
 * @param onCardClick 카드 클릭 시 동작
 */

@Composable
fun HapHapCard(
    type: CardType,
    imageUrl: String,
    text: String,
    stage: String,
    dDay: Int,
    company: String,
    description: String,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(HapHapTheme.colors.gray100)
            .noRippleClickable(onClick = onCardClick)
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            UrlImage(
                url = imageUrl,
                placeholderDrawable = R.drawable.ic_launcher_background,
                contentScale = ContentScale.Crop,
                contentDescription = company,
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(type.imageRatio),
            )

            HapHapStatusChip(
                text = text,
                type = StatusChipType.CATEGORY,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(start = 10.dp, bottom = 8.dp),
            )
        }

        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 10.dp)
        ) {
            HapHapDeadlineChip(
                stage = stage,
                dDay = dDay,
            )

            Spacer(modifier = Modifier.height(2.dp))

            Column(modifier = Modifier.padding(start = 2.dp)) {
                Text(
                    text = company,
                    style = HapHapTheme.typography.body.sb14,
                    color = HapHapTheme.colors.gray700,
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = description,
                    style = HapHapTheme.typography.caption.sb12,
                    color = HapHapTheme.colors.gray600,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}

@Preview
@Composable
private fun HapHapCardPreview() {
    HapHapTheme {
        Row(
            modifier = Modifier.padding(all = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            HapHapCard(
                type = CardType.BIG,
                imageUrl = "",
                text = "개발/데이터",
                stage = "1차 면접",
                dDay = 2,
                company = "카카오",
                description = "공고명공고명공고명공고명공고명공고명공고명공고명",
                onCardClick = {},
                modifier = Modifier.width(186.dp),
            )
            HapHapCard(
                type = CardType.SMALL,
                imageUrl = "",
                text = "개발/데이터",
                stage = "1차 면접",
                dDay = 2,
                company = "카카오",
                description = "공고명공고명공고명공고명공고명공고명공고명공고명",
                onCardClick = {},
                modifier = Modifier.width(155.dp),
            )
        }
    }
}
