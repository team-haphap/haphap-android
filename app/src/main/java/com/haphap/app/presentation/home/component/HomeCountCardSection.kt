package com.haphap.app.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.home.CountCardModel

@Composable
fun HomeCountCardSection(
    countCardModel: CountCardModel,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
    ) {
        HomeCountCard(
            imageRes = R.drawable.img_home_1,
            labelBold = "누적 공유",
            labelMedium = "결과",
            count = countCardModel.cumulatedCount,
            modifier = Modifier.weight(1f),
        )
        HomeCountCard(
            imageRes = R.drawable.img_home_2,
            labelBold = "진행 중인",
            labelMedium = "공고",
            count = countCardModel.onGoingCount,
            modifier = Modifier.weight(1f),
        )
        HomeCountCard(
            imageRes = R.drawable.img_home_3,
            labelBold = "오늘 발표된",
            labelMedium = "공고",
            count = countCardModel.announcedCount,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun HomeCountCard(
    imageRes: Int,
    labelBold: String,
    labelMedium: String,
    count: Int,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(HapHapTheme.colors.white),
    ) {
        Image(
            painter = painterResource(id = imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(100f / 62f),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = HapHapTheme.colors.gray100,
                    shape = RoundedCornerShape(
                        bottomStart = 8.dp,
                        bottomEnd = 8.dp,
                    ),
                )
                .padding(start = 10.dp, end = 10.dp, top = 6.dp, bottom = 4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = labelBold,
                    style = HapHapTheme.typography.caption.b12,
                    color = HapHapTheme.colors.gray600,
                )

                Spacer(modifier = Modifier.width(2.dp))

                Text(
                    text = labelMedium,
                    style = HapHapTheme.typography.caption.m12,
                    color = HapHapTheme.colors.gray600,
                )
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = count.toString(),
                    style = HapHapTheme.typography.subtitle.sb24,
                    color = HapHapTheme.colors.primary500,
                )
                Text(
                    text = "개",
                    style = HapHapTheme.typography.body.b18,
                    color = HapHapTheme.colors.primary100,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeCountCardSectionPreview() {
    HapHapTheme {
        Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 16.dp)) {
            HomeCountCardSection(
                countCardModel = CountCardModel(
                    cumulatedCount = 37,
                    onGoingCount = 37,
                    announcedCount = 37,
                ),
            )
        }
    }
}