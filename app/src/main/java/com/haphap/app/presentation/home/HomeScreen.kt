package com.haphap.app.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.button.HapHapRefreshButton
import com.haphap.app.core.designsystem.component.textfield.HapHapSearchTextField
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.home.BannerListModel
import com.haphap.app.data.model.home.CountCardModel
import com.haphap.app.data.model.home.RecentCardModel
import com.haphap.app.data.model.home.TodayExpectedCardModel
import com.haphap.app.presentation.home.component.HomeBannerSection
import com.haphap.app.presentation.home.component.HomeCardTitle
import com.haphap.app.presentation.home.component.HomeCountCardSection
import com.haphap.app.presentation.home.component.HomeListCardSection
import com.haphap.app.presentation.home.component.HomeRecentCardSection
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onRecentCardClick = {},
        onListCardClick = {},
        modifier = modifier,
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeContract.State,
    onRecentCardClick: (Int) -> Unit,
    onListCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = HapHapTheme.colors.white),
            contentPadding = PaddingValues(bottom = 54.dp),
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.img_text_logo),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 20.dp, top = 10.dp, bottom = 12.dp)
                        .height(20.dp)
                        .aspectRatio(124f / 20f),
                )
            }

            item {
                val state = rememberTextFieldState(initialText = "")

                HapHapSearchTextField(
                    state = state,
                    placeholder = "공고명을 검색해보세요!",
                    onSearch = {},
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
                )

            }

            item {
                HomeBannerSection(
                    bannerList = uiState.bannerList
                )
            }

            item {
                uiState.countCardModel?.let { countData ->
                    HomeCountCardSection(
                        countCardModel = countData,
                    )
                }
            }

            item {
                HorizontalDivider(
                    thickness = 1.dp,
                    color = HapHapTheme.colors.gray100,
                )
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))

                HomeCardTitle(
                    title = "최근 결과가 올라온 공고",
                    description = "지원자 결과가 활발하게 공유되고 있는 공고를 확인해요",
                    isMore = true,
                    onMoreClick = {},
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))

                HomeRecentCardSection(
                    recentCardList = uiState.recentCardList,
                    onRecentCardClick = onRecentCardClick,
                )

                Spacer(modifier = Modifier.height(24.dp))
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))

                HomeCardTitle(
                    title = "오늘 발표 예상 공고",
                    description = "과거 패턴을 바탕으로 오늘 발표 가능성이 높은 공고를 확인해요",
                    isMore = false,
                )
            }

            item {
                HomeListCardSection(
                    todayExpectedCardList = uiState.todayExpectedCardList,
                    onListCardClick = onListCardClick,
                )
            }
        }

        HapHapRefreshButton(
            onButtonClick = {},
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 10.dp, end = 20.dp),
        )
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    HapHapTheme {
        HomeScreen(
            uiState = HomeContract.State(
                bannerList = persistentListOf(
                    BannerListModel(id = 1, imageUrl = ""),
                    BannerListModel(id = 2, imageUrl = ""),
                    BannerListModel(id = 3, imageUrl = ""),
                    BannerListModel(id = 4, imageUrl = ""),
                    BannerListModel(id = 5, imageUrl = ""),
                ),

                countCardModel = CountCardModel(
                    cumulatedCount = 37,
                    onGoingCount = 37,
                    announcedCount = 37,
                ),

                recentCardList = persistentListOf(
                    RecentCardModel(
                        id = 1,
                        imageUrl = "",
                        text = "개발/데이터",
                        stage = "서류",
                        dDay = 2,
                        company = "카카오",
                        description = "공고 설명",
                    ),
                    RecentCardModel(
                        id = 2,
                        imageUrl = "",
                        text = "개발/데이터",
                        stage = "서류",
                        dDay = 2,
                        company = "카카오",
                        description = "공고 설명",
                    ),
                    RecentCardModel(
                        id = 3,
                        imageUrl = "",
                        text = "인사",
                        stage = "서류",
                        dDay = 2,
                        company = "카카오",
                        description = "공고 설명",
                    ),
                    RecentCardModel(
                        id = 4,
                        imageUrl = "",
                        text = "인사",
                        stage = "서류",
                        dDay = 2,
                        company = "카카오",
                        description = "공고 설명",
                    ),
                ),

                todayExpectedCardList = persistentListOf(
                    TodayExpectedCardModel(
                        id = 1,
                        imageUrl = "",
                        companyName = "카카오",
                        category = "개발/데이터",
                        stageName = "전형",
                        title = "2026 신입 공개채용",
                    ),
                    TodayExpectedCardModel(
                        id = 2,
                        imageUrl = "",
                        companyName = "카카오",
                        category = "개발/데이터",
                        stageName = "전형",
                        title = "2026 신입 공개채용",
                    ),
                    TodayExpectedCardModel(
                        id = 3,
                        imageUrl = "",
                        companyName = "카카오",
                        category = "개발/데이터",
                        stageName = "전형",
                        title = "2026 신입 공개채용",
                    ),
                ),
            ),
            onRecentCardClick = {},
            onListCardClick = {},
        )
    }
}
