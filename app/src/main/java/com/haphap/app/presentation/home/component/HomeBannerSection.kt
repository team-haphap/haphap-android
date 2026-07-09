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
import com.haphap.app.data.model.home.BannerItemModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay

@Composable
fun HomeBannerSection(
    bannerList: ImmutableList<BannerItemModel>,
    modifier: Modifier = Modifier,
    state: HomeBannerState = rememberHomeBannerState(bannerList = bannerList),
) {
    if (bannerList.isEmpty()) return

    state.HandleAutoScroll()

    Column(modifier = modifier.padding(top = 4.dp)) {
        HorizontalPager(
            state = state.pagerState,
            contentPadding = PaddingValues(horizontal = 30.dp),
            pageSpacing = 12.dp,
        ) { page ->
            val index = page % bannerList.size
            BannerCard(imageUrl = bannerList[index].imageUrl)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 6.dp,
                alignment = Alignment.CenterHorizontally,
            ),
        ) {
            val currentIndex = state.pagerState.currentPage % bannerList.size

            repeat(bannerList.size) { index ->
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
) {
    @Composable
    fun HandleAutoScroll() {
        val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

        LaunchedEffect(isDragged) {
            if (!isDragged) {
                while (true) {
                    delay(AUTO_SCROLL_DELAY)
                    if (pagerState.pageCount > 0) {
                        val nextPage = (pagerState.currentPage + 1) % pagerState.pageCount
                        pagerState.animateScrollToPage(nextPage)
                    }
                }
            }
        }
    }

    companion object {
        private const val AUTO_SCROLL_DELAY = 3000L
    }
}

@Composable
private fun rememberHomeBannerState(
    bannerList: ImmutableList<BannerItemModel>
): HomeBannerState {
    val pagerState = rememberPagerState(
        initialPage = if (bannerList.isEmpty()) 0 else (Int.MAX_VALUE / 2) - (Int.MAX_VALUE / 2 % bannerList.size),
        pageCount = { if (bannerList.isEmpty()) 0 else Int.MAX_VALUE }
    )
    return remember(pagerState) {
        HomeBannerState(pagerState)
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
            .clip(RoundedCornerShape(12.dp))
    ) {
        UrlImage(
            modifier = Modifier
                .aspectRatio(300f / 198f)
                .height(198.dp),
            url = imageUrl,
            placeholderDrawable = R.drawable.ic_launcher_background,
            contentDescription = null,
            contentScale = ContentScale.Crop,
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
            bannerList = persistentListOf(
                BannerItemModel(id = 1, imageUrl = ""),
                BannerItemModel(id = 2, imageUrl = ""),
                BannerItemModel(id = 3, imageUrl = ""),
                BannerItemModel(id = 4, imageUrl = ""),
                BannerItemModel(id = 5, imageUrl = ""),
            )
        )
    }
}