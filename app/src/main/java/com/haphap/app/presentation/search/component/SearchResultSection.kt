package com.haphap.app.presentation.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.card.HapHapCard
import com.haphap.app.core.designsystem.component.chip.FilterChipContent
import com.haphap.app.core.designsystem.component.chip.HapHapFilterChip
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.CardType
import com.haphap.app.data.model.search.ChipListModel
import com.haphap.app.data.model.search.SearchResultModel
import com.haphap.app.presentation.category.CategoryChipState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


@Composable
fun SearchResultSection(
    chipList: ImmutableList<ChipListModel>,
    selectedChips: ImmutableList<String>,
    onFilterClick: (String) -> Unit,
    searchResultList: ImmutableList<SearchResultModel>,
    onCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(vertical = 2.dp, horizontal = 20.dp)
        ) {
            item {
                HapHapFilterChip(
                    content = FilterChipContent.IconContent(
                        iconRes = R.drawable.ic_filter_20,
                    ),
                    onFilterClick = {},
                    isFilterSelected = false
                )

                Spacer(modifier = Modifier.width(8.dp))
            }

            items(
                items = chipList,
                key = { it.id },
            ) {
                HapHapFilterChip(
                    content = FilterChipContent.TextContent(it.category),
                    onFilterClick = { onFilterClick(it.category) },
                    isFilterSelected = selectedChips.contains(it.category),
                )

                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            items(
                items = searchResultList,
                key = { it.id }
            ){
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
}

@Preview (showBackground = true)
@Composable
private fun SearchResultSectionPreview() {
    HapHapTheme {
        var categoryChipState by remember { mutableStateOf(CategoryChipState()) }

        SearchResultSection(
            chipList = categoryChipState.chipList,
            selectedChips = categoryChipState.selectedChips,
            onFilterClick = { category ->
                categoryChipState = categoryChipState.toggle(category)
            },
            searchResultList = persistentListOf(
                SearchResultModel(
                    id = 1,
                    imageUrl = "",
                    category = "개발",
                    stage = "서류",
                    dDay = 2,
                    title = "카카오",
                    content = "공고 설명",
                ),
                SearchResultModel(
                    id = 2,
                    imageUrl = "",
                    category = "개발",
                    stage = "서류",
                    dDay = 2,
                    title = "카카오",
                    content = "공고 설명",
                ),
                SearchResultModel(
                    id = 3,
                    imageUrl = "",
                    category = "개발",
                    stage = "서류",
                    dDay = 2,
                    title = "카카오",
                    content = "공고 설명",
                ),
                SearchResultModel(
                    id = 4,
                    imageUrl = "",
                    category = "개발",
                    stage = "서류",
                    dDay = 2,
                    title = "카카오",
                    content = "공고 설명",
                ),
            ),
            onCardClick = {},
        )
    }
}
