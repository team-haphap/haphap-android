package com.haphap.app.presentation.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.card.HapHapCard
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.CardType
import com.haphap.app.core.extensions.OnBottomReached
import com.haphap.app.data.model.search.SearchResultItemModel
import com.haphap.app.presentation.common.component.HapHapCategoryChipList
import com.haphap.app.presentation.common.model.ChipListModel
import com.haphap.app.presentation.common.state.CategoryChipState
import com.haphap.app.presentation.search.SearchUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


@Composable
fun SearchResultSection(
    searchResultUiState: SearchUiState,
    listState: LazyGridState,
    chipList: ImmutableList<ChipListModel>,
    selectedChips: ImmutableList<String>,
    onFilterClick: (String) -> Unit,
    searchResultList: ImmutableList<SearchResultItemModel>,
    onCardClick: (Int) -> Unit,
    onLoadMoreSearchList: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val currentIsLoading = searchResultUiState is SearchUiState.Loading

    Column(
        modifier = modifier,
    ) {
        HapHapCategoryChipList(
            chipList = chipList,
            selectedChips = selectedChips,
            onFilterClick = onFilterClick,
        )

        Spacer(modifier = Modifier.height(10.dp))

        listState.OnBottomReached(
            threshold = 3,
            onLoadMore = onLoadMoreSearchList,
            isLoading = currentIsLoading,
        )

        when(searchResultUiState){
            SearchUiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    state = listState,
                    contentPadding = PaddingValues(vertical = 2.dp, horizontal = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    items(
                        items = searchResultList,
                        key = { it.id }
                    ) {
                        HapHapCard(
                            type = CardType.SMALL,
                            imageUrl = it.imageUrl,
                            text = it.category,
                            stage = it.stage,
                            dDay = it.dDay,
                            company = it.companyName,
                            description = it.title,
                            onCardClick = { onCardClick(it.id) },
                        )
                    }
                }
            }

            SearchUiState.Empty -> {// TODO: 빈화면 추가 }
            }
            is SearchUiState.Failure, SearchUiState.Idle -> {}
            SearchUiState.Loading ->  {}
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchResultSectionPreview() {
    HapHapTheme {
        var categoryChipState by remember { mutableStateOf(CategoryChipState()) }

        SearchResultSection(
            searchResultUiState = SearchUiState.Success,
            listState = rememberLazyGridState(),
            chipList = categoryChipState.chipList,
            selectedChips = categoryChipState.selectedChips,
            onFilterClick = { category ->
                categoryChipState = categoryChipState.toggle(category)
            },
            searchResultList = persistentListOf(
                SearchResultItemModel(
                    id = 1,
                    imageUrl = "",
                    companyName = "카카오",
                    category = "개발",
                    stage = "서류",
                    dDay = 2,
                    title = "공고 설명",
                ),
                SearchResultItemModel(
                    id = 2,
                    imageUrl = "",
                    companyName = "카카오",
                    category = "개발",
                    stage = "서류",
                    dDay = 2,
                    title = "공고 설명",
                ),
                SearchResultItemModel(
                    id = 3,
                    imageUrl = "",
                    companyName = "카카오",
                    category = "개발",
                    stage = "서류",
                    dDay = 2,
                    title = "공고 설명",
                ),
                SearchResultItemModel(
                    id = 4,
                    imageUrl = "",
                    companyName = "카카오",
                    category = "개발",
                    stage = "서류",
                    dDay = 2,
                    title = "공고 설명",
                ),
            ),
            onCardClick = {},
            onLoadMoreSearchList = {},
        )
    }
}
