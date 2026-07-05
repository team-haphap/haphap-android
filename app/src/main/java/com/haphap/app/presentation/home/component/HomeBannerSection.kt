package com.haphap.app.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun HomeBannerSection(
    banners: List<String>,
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState(pageCount = { banners.size })

    Column(modifier = modifier.padding(vertical = 8.dp)) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 20.dp),
            pageSpacing = 10.dp,
        ) { page ->
            BannerCard(imageUrl = banners[page])
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 6.dp,
                alignment = Alignment.CenterHorizontally,
            ),
        ) {
            repeat(banners.size) { index ->
                val isSelected = pagerState.currentPage == index

                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color = if (isSelected) HapHapTheme.colors.primary100 else HapHapTheme.colors.gray200)
                )
            }
        }
    }
}

@Composable
private fun BannerCard(
    imageUrl: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(300f / 198f)
            .clip(RoundedCornerShape(12.dp))
    ) {
        UrlImage(
            url = imageUrl,
            placeholderDrawable = R.drawable.ic_launcher_background,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.matchParentSize(),
        )

        Column(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 16.dp, bottom = 14.dp),
        ) {
            Text(
                text = "지원 이후, 보이지 않던\n기다림을 더욱 선명하게",
                style = HapHapTheme.typography.body.b18,
                color = HapHapTheme.colors.gray800,
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "같은 공고 지원자들의 결과를 확인해보세요!",
                style = HapHapTheme.typography.caption.sb10,
                color = HapHapTheme.colors.gray400,
            )
        }
    }
}

@Preview
@Composable
private fun HomeBannerSectionPreview() {
    HapHapTheme {
        HomeBannerSection(
            banners = listOf("","","","","")
        )
    }
}