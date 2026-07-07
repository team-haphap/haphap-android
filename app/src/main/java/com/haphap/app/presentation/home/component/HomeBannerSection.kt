package com.haphap.app.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.collectIsDraggedAsState
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
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import kotlinx.coroutines.delay

@Composable
fun HomeBannerSection(
    banners: List<String>,
    modifier: Modifier = Modifier,
    state: HomeBannerState = rememberHomeBannerState(banners = banners),
) {
    if (banners.isEmpty()) return

    state.HandleAutoScroll()

    Column(modifier = modifier.padding(vertical = 8.dp)) {
        HorizontalPager(
            state = state.pagerState,
            contentPadding = PaddingValues(horizontal = 20.dp),
            pageSpacing = 10.dp,
        ) { page ->
            val index = page % banners.size
            BannerCard(imageUrl = banners[index])
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
                val currentIndex = state.pagerState.currentPage % banners.size
                val isSelected = currentIndex == index

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

class HomeBannerState(
    val pagerState: PagerState,
    private val autoScrollDelay: Long = 5000L
) {
    @Composable
    fun HandleAutoScroll() {
        val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

        LaunchedEffect(isDragged) {
            if (!isDragged) {
                while (true) {
                    delay(autoScrollDelay)
                    if (pagerState.pageCount > 0) {
                        val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                        pagerState.animateScrollToPage(nextPage)
                    }
                }
            }
        }
    }
}

@Composable
private fun rememberHomeBannerState(
    banners: List<String>,
    autoScrollDelay: Long = 5000L
): HomeBannerState {
    val pagerState = rememberPagerState(
        initialPage = if (banners.isEmpty()) 0 else (Int.MAX_VALUE / 2) - (Int.MAX_VALUE / 2 % banners.size),
        pageCount = { if (banners.isEmpty()) 0 else Int.MAX_VALUE }
    )
    return remember(pagerState) {
        HomeBannerState(pagerState, autoScrollDelay)
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