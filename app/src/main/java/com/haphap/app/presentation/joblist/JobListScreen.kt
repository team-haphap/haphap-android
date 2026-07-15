package com.haphap.app.presentation.joblist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells.Fixed
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.core.designsystem.component.card.HapHapCard
import com.haphap.app.core.designsystem.component.searchbar.HapHapSearchBar
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.CardType
import com.haphap.app.data.model.list.JobItemModel
import com.haphap.app.presentation.common.component.HapHapCategoryChipList
import com.haphap.app.presentation.joblist.component.IconEmptyComponent
import kotlinx.collections.immutable.persistentListOf

@Composable
fun JobListRoute(
    navigateToSearch: () -> Unit,
    navigateToJobDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: JobListViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val gridState = rememberLazyGridState()

    LaunchedEffect(
        uiState.categoryChipState.selectedChips
    ) {
        gridState.scrollToItem(0)
    }

    JobListScreen(
        uiState = uiState,
        gridState = gridState,
        onSearchBarClick = navigateToSearch,
        onFilterClick = { viewModel.updateSelectedChips(it) },
        onCardClick = { navigateToJobDetail(it) },
        modifier = modifier,
    )
}

@Composable
private fun JobListScreen(
    uiState: JobListContract.State,
    gridState: LazyGridState,
    onSearchBarClick: () -> Unit,
    onFilterClick: (String) -> Unit,
    onCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(color = HapHapTheme.colors.white)
            .padding(top = 10.dp),
    ) {
        HapHapSearchBar(
            placeholder = "공고명을 검색해보세요!",
            onSearchBarClick = onSearchBarClick,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(22.dp))

        HapHapCategoryChipList(
            chipList = uiState.categoryChipState.chipList,
            selectedChips = uiState.categoryChipState.selectedChips,
            onFilterClick = onFilterClick,
        )

        Spacer(modifier = Modifier.height(10.dp))

        when (uiState.jobListUiState) {
            JobListUiState.Success -> {
                LazyVerticalGrid(
                    columns = Fixed(2),
                    state = gridState,
                    contentPadding = PaddingValues(vertical = 2.dp, horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(
                        items = uiState.jobList,
                        key = { it.id }
                    ) {
                        HapHapCard(
                            type = CardType.SMALL,
                            imageUrl = it.imageUrl,
                            text = it.category,
                            stage = it.stage,
                            dDay = it.dDay,
                            company = it.title,
                            description = it.content,
                            onCardClick = { onCardClick(it.id) },
                        )
                    }
                }
            }

            JobListUiState.Empty -> {
                IconEmptyComponent(
                    text = "해당 카테고리에 등록된 공고가 없습니다",
                    modifier = Modifier.padding(top = 190.dp),
                )
            }

            is JobListUiState.Failure, JobListUiState.Idle, JobListUiState.Loading -> {}

        }
    }
}


@Preview(showBackground = true)
@Composable
private fun JobListScreenPreview() {
    HapHapTheme {
        JobListScreen(
            uiState = JobListContract.State(
                jobList = persistentListOf(
                    JobItemModel(
                        id = 1,
                        imageUrl = "",
                        category = "개발",
                        stage = "서류",
                        dDay = 2,
                        title = "카카오",
                        content = "공고 설명",
                    ),
                    JobItemModel(
                        id = 2,
                        imageUrl = "",
                        category = "개발",
                        stage = "서류",
                        dDay = 2,
                        title = "카카오",
                        content = "공고 설명",
                    ),
                    JobItemModel(
                        id = 3,
                        imageUrl = "",
                        category = "개발",
                        stage = "서류",
                        dDay = 2,
                        title = "카카오",
                        content = "공고 설명",
                    ),
                    JobItemModel(
                        id = 4,
                        imageUrl = "",
                        category = "개발",
                        stage = "서류",
                        dDay = 2,
                        title = "카카오",
                        content = "공고 설명",
                    ),
                )
            ),
            gridState = rememberLazyGridState(),
            onSearchBarClick = {},
            onFilterClick = {},
            onCardClick = {},
        )
    }
}
