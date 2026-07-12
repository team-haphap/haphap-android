package com.haphap.app.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.haphap.app.core.designsystem.component.searchbar.HapHapSearchBar
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.home.BannerItemModel
import com.haphap.app.data.model.home.CountCardModel
import com.haphap.app.data.model.home.RecentCardModel
import com.haphap.app.data.model.home.TodayExpectedCardModel
import com.haphap.app.presentation.common.component.HapHapCategoryChipList
import com.haphap.app.presentation.home.component.HomeBannerSection
import com.haphap.app.presentation.home.component.HomeCardTitle
import com.haphap.app.presentation.home.component.HomeCountCardSection
import com.haphap.app.presentation.home.component.HomeListCardSection
import com.haphap.app.presentation.home.component.HomeRecentCardSection
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    navigateToSearch: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        uiState = uiState,
        onSearchBarClick = navigateToSearch,
        onMoreClick = {},
        onFilterClick = { viewModel.updateSelectedChips(it) },
        onRecentCardClick = {},
        onListCardClick = {},
        onButtonClick = {},
        modifier = modifier,
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeContract.State,
    onSearchBarClick: () -> Unit,
    onMoreClick: () -> Unit,
    onFilterClick: (String) -> Unit,
    onRecentCardClick: (Int) -> Unit,
    onListCardClick: (Int) -> Unit,
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = HapHapTheme.colors.white),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Image(
                painter = painterResource(id = R.drawable.img_text_logo),
                contentDescription = null,
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .height(20.dp),
            )

            HapHapSearchBar(
                placeholder = "공고명을 검색해보세요!",
                onSearchBarClick = onSearchBarClick,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(bottom = 12.dp),
            ) {
                item {
                    Spacer(modifier = Modifier.height(8.dp))

                    HomeBannerSection(
                        bannerList = uiState.bannerList
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    uiState.countCardModel?.let { countData ->
                        HomeCountCardSection(
                            countCardModel = countData,
                        )
                    }

                    Spacer(modifier = Modifier.height(24.dp))
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
                        onMoreClick = onMoreClick,
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    HapHapCategoryChipList(
                        chipList = uiState.categoryChipState.chipList,
                        selectedChips = uiState.categoryChipState.selectedChips,
                        onFilterClick = onFilterClick,
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    HomeRecentCardSection(
                        recentCardList = uiState.recentCardList,
                        onRecentCardClick = onRecentCardClick,
                    )

                    Spacer(modifier = Modifier.height(36.dp))
                }

                item {
                    HomeCardTitle(
                        title = "오늘 발표 예상 공고",
                        description = "과거 패턴을 바탕으로 오늘 발표 가능성이 높은 공고를 확인해요",
                        isMore = false,
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(12.dp))

                    HomeListCardSection(
                        todayExpectedCardList = uiState.todayExpectedCardList,
                        onListCardClick = onListCardClick,
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        HapHapRefreshButton(
            onButtonClick = onButtonClick,
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
                    BannerItemModel(id = 1, imageUrl = ""),
                    BannerItemModel(id = 2, imageUrl = ""),
                    BannerItemModel(id = 3, imageUrl = ""),
                    BannerItemModel(id = 4, imageUrl = ""),
                    BannerItemModel(id = 5, imageUrl = ""),
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
                        category = "개발/데이터",
                        nextStage = "서류",
                        dayUntilNextStage = 2,
                        companyName = "카카오",
                        title = "공고 설명",
                    ),
                    RecentCardModel(
                        id = 2,
                        imageUrl = "",
                        category = "개발/데이터",
                        nextStage = "서류",
                        dayUntilNextStage = 2,
                        companyName = "카카오",
                        title = "공고 설명",
                    ),
                    RecentCardModel(
                        id = 3,
                        imageUrl = "",
                        category = "인사",
                        nextStage = "서류",
                        dayUntilNextStage = 2,
                        companyName = "카카오",
                        title = "공고 설명",
                    ),
                    RecentCardModel(
                        id = 4,
                        imageUrl = "",
                        category = "인사",
                        nextStage = "서류",
                        dayUntilNextStage = 2,
                        companyName = "카카오",
                        title = "공고 설명",
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
            onSearchBarClick = {},
            onMoreClick = {},
            onFilterClick = {},
            onRecentCardClick = {},
            onListCardClick = {},
            onButtonClick = {},
        )
    }
}
