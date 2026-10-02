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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.searchbar.HapHapSearchBar
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.home.BannerItemModel
import com.haphap.app.data.model.home.MyApplicationCardModel
import com.haphap.app.data.model.home.PopularJobCardModel
import com.haphap.app.data.model.home.RecentJobCardModel
import com.haphap.app.presentation.home.component.HomeBannerSection
import com.haphap.app.presentation.home.component.HomeMyApplicationSection
import com.haphap.app.presentation.home.component.HomePopularJobSection
import com.haphap.app.presentation.home.component.HomeRecentJobSection
import com.haphap.app.presentation.home.component.HomeTopBar
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    navigateToSearch: () -> Unit,
    navigateToJobList: () -> Unit,
    navigateToJobDetail: (Int) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    val lifecycleOwner = LocalLifecycleOwner.current
    val uriHandler = LocalUriHandler.current

    LaunchedEffect(lifecycleOwner) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is HomeContract.SideEffect.NavigateToSearch -> {
                        navigateToSearch()
                    }

                    is HomeContract.SideEffect.NavigateToJobList -> {
                        navigateToJobList()
                    }

                    is HomeContract.SideEffect.NavigateToJobDetail -> {
                        navigateToJobDetail(sideEffect.postingId)
                    }

                    is HomeContract.SideEffect.OpenUrl -> {
                        runCatching { uriHandler.openUri(sideEffect.url) }
                    }
                }
            }
        }
    }

    HomeScreen(
        uiState = uiState,
        onSearchBarClick = viewModel::onSearchBarClick,
        onBannerClick = viewModel::onBannerClick,
        onFilterClick = { viewModel.updateSelectedChips(it) },
        onRecentCardClick = viewModel::onCardClick,
        onListCardClick = viewModel::onCardClick,
        onPopularCardClick = viewModel::onCardClick,
        modifier = modifier,
    )
}

@Composable
private fun HomeScreen(
    uiState: HomeContract.State,
    onSearchBarClick: () -> Unit,
    onBannerClick: (String?) -> Unit,
    onFilterClick: (String) -> Unit,
    onRecentCardClick: (Int) -> Unit,
    onListCardClick: (Int) -> Unit,
    onPopularCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = HapHapTheme.colors.white),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            HomeTopBar(
                point = 100,
                onPointClick = {}, //Todo: 추후 마이페이지 개발 후 연결
                onProfileClick = {},
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
                        bannerList = uiState.bannerList,
                        onBannerClick = onBannerClick,
                    )
                }

                if (uiState.myApplicationCardList.isNotEmpty()) {
                    item {
                        Spacer(modifier = Modifier.height(8.dp))

                        HomeMyApplicationSection(
                            myApplicationCardList = uiState.myApplicationCardList,
                            onListCardClick = onListCardClick,
                            onMoreClick = {}, //Todo: 추후 마이페이지 개발 후 연결
                        )
                    }
                }

                if (uiState.recentJobCardList.isNotEmpty()) {
                    item {
                        HomeRecentJobSection(
                            recentJobCardList = uiState.recentJobCardList,
                            onRecentCardClick = onRecentCardClick,
                        )

                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

                item {
                    HomePopularJobSection(
                        popularJobCardList = uiState.popularJobCardList,
                        categoryChipState = uiState.categoryChipState,
                        onFilterClick = onFilterClick,
                        onPopularCardClick = onPopularCardClick,

                    )
                }

                item {
                    Spacer(modifier = Modifier.height(16.dp))

                    Image(
                        painter = painterResource(id = R.drawable.img_home_banner), //Todo: 추후 핸드오프 예정
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp),
                    )
                }
            }
        }
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

                myApplicationCardList = persistentListOf(
                    MyApplicationCardModel(
                        id = 1,
                        logoImageUrl = "",
                        title = "2026 신입 공개채용",
                        position = "개발/데이터",
                        currentStageStatus = "서류 발표 중",
                        dDayLabel = "D-2",
                        companyName = "카카오",
                    ),
                    MyApplicationCardModel(
                        id = 2,
                        logoImageUrl = "",
                        title = "2026 신입 공개채용",
                        position = "개발/데이터",
                        currentStageStatus = "서류 발표 중",
                        dDayLabel = "D-2",
                        companyName = "카카오",
                    ),
                ),

                recentJobCardList = persistentListOf(
                    RecentJobCardModel(
                        id = 1,
                        logoImageUrl = "",
                        title = "공고명",
                        position = "개발/데이터",
                        nextStage = "서류",
                        dDayLabel = "D-2",
                        companyName = "카카오",
                    ),
                    RecentJobCardModel(
                        id = 2,
                        logoImageUrl = "",
                        title = "공고명",
                        position = "인사",
                        nextStage = "서류",
                        dDayLabel = "D-2",
                        companyName = "카카오",
                    ),
                    RecentJobCardModel(
                        id = 3,
                        logoImageUrl = "",
                        title = "공고명",
                        position = "개발/데이터",
                        nextStage = "서류",
                        dDayLabel = "D-2",
                        companyName = "카카오",
                    ),
                    RecentJobCardModel(
                        id = 4,
                        logoImageUrl = "",
                        title = "공고명",
                        position = "인사",
                        nextStage = "서류",
                        dDayLabel = "D-2",
                        companyName = "카카오",
                    ),
                ),

                popularJobCardList = persistentListOf(
                    PopularJobCardModel(
                        id = 1,
                        logoImageUrl = "",
                        title = "공고명",
                        position = "개발/데이터",
                        nextStage = "서류",
                        dDayLabel = "D-2",
                        companyName = "카카오",
                    ),
                    PopularJobCardModel(
                        id = 2,
                        logoImageUrl = "",
                        title = "공고명",
                        position = "인사",
                        nextStage = "서류",
                        dDayLabel = "D-2",
                        companyName = "카카오",
                    ),
                    PopularJobCardModel(
                        id = 3,
                        logoImageUrl = "",
                        title = "공고명",
                        position = "개발/데이터",
                        nextStage = "서류",
                        dDayLabel = "D-2",
                        companyName = "카카오",
                    ),
                    PopularJobCardModel(
                        id = 4,
                        logoImageUrl = "",
                        title = "공고명",
                        position = "인사",
                        nextStage = "서류",
                        dDayLabel = "D-2",
                        companyName = "카카오",
                    ),
                ),
            ),
            onSearchBarClick = {},
            onBannerClick = {},
            onFilterClick = {},
            onRecentCardClick = {},
            onListCardClick = {},
            onPopularCardClick = {},
        )
    }
}
