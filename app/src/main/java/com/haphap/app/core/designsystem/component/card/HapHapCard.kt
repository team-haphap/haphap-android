package com.haphap.app.core.designsystem.component.card

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

enum class HapHapCardType { BIG, SMALL }

/**
 * 공고 카드 공통 컴포넌트입니다.
 * BIG/SMALL 타입에 따라 이미지 비율, 카드 크기, 텍스트 스타일이 변경됩니다.
 *
 * @param type 카드 타입 (BIG, SMALL)
 * @param imageUrl 카드 이미지 URL
 * @param company 회사명
 * @param description 기업 설명
 * @param onCardClick 카드 클릭 시 동작
 */

@Composable
fun HapHapCard(
    type: HapHapCardType,
    imageUrl: String,
    company: String,
    description: String,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val imageRatio = when (type) {
        HapHapCardType.BIG -> 170f / 82f
        HapHapCardType.SMALL -> 131f / 82f
    }
    val cardRadius = when (type) {
        HapHapCardType.BIG -> 8.dp
        HapHapCardType.SMALL -> 12.dp
    }
    val spacerHeight = when (type) {
        HapHapCardType.BIG -> 12.dp
        HapHapCardType.SMALL -> 6.dp
    }
    val spacerBottom = when (type) {
        HapHapCardType.BIG -> 12.dp
        HapHapCardType.SMALL -> 9.dp
    }
    val companyStyle = when (type) {
        HapHapCardType.BIG -> HapHapTheme.typography.body.sb16
        HapHapCardType.SMALL -> HapHapTheme.typography.body.sb14
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(cardRadius))
            .background(HapHapTheme.colors.gray100)
            .noRippleClickable(onClick = onCardClick)
            .padding(start = 12.dp, end = 12.dp, top = 12.dp),
    ) {
        UrlImage(
            url = imageUrl,
            placeholderDrawable = R.drawable.img_kakao_card,
            contentScale = ContentScale.Crop,
            contentDescription = company,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(imageRatio)
                .clip(RoundedCornerShape(8.dp)),
        )

        Spacer(modifier = Modifier.height(spacerHeight))

        //Todo: 추후 status chip 추가
        Spacer(modifier = Modifier.height(17.dp))

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = company,
            style = companyStyle,
            color = HapHapTheme.colors.gray700,
        )

        Spacer(modifier = Modifier.height(2.dp))

        Text(
            text = description,
            style = HapHapTheme.typography.caption.sb12,
            color = HapHapTheme.colors.gray600,
        )

        Spacer(modifier = Modifier.height(spacerBottom))
    }
}

@Preview
@Composable
private fun HapHapCardPreview() {
    HapHapTheme {
        Row(
            modifier = Modifier.padding(all = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            HapHapCard(
                type = HapHapCardType.BIG,
                imageUrl = "",
                company = "카카오",
                description = "기업에 대한 설명",
                onCardClick = {},
                modifier = Modifier.width(194.dp)
            )
            HapHapCard(
                type = HapHapCardType.SMALL,
                imageUrl = "",
                company = "카카오",
                description = "기업에 대한 설명",
                onCardClick = {},
                modifier = Modifier.width(155.dp)
            )
        }
    }
}
