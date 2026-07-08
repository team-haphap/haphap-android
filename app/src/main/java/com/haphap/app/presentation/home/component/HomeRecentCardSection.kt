package com.haphap.app.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
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
import com.haphap.app.data.model.home.ChipListModel
import com.haphap.app.data.model.home.RecentCardModel
import com.haphap.app.presentation.home.HomeContract.State.Companion.DEFAULT_CHIP_LIST
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private const val MAX_RECENT_CARD_COUNT = 8

@Composable
fun HomeRecentCardSection(
    chipList: ImmutableList<ChipListModel>,
    selectedChips: ImmutableList<Int>,
    onFilterClick: (Int) -> Unit,
    recentCardList: ImmutableList<RecentCardModel>,
    onCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column() {
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(20.dp)
        ) {
            item {
                HapHapFilterChip(
                    content = FilterChipContent.IconContent(
                        iconRes = R.drawable.ic_filter_20,
                    ),
                    onFilterClick = {},
                    isFilterSelected = false,
                )

                Spacer(modifier = Modifier.width(8.dp))
            }

            items(
                items = chipList,
                key = { it.id },
            ) {
                HapHapFilterChip(
                    content = FilterChipContent.TextContent(it.category),
                    onFilterClick = { onFilterClick(it.id) },
                    isFilterSelected = selectedChips.contains(it.id),
                )

                Spacer(modifier = Modifier.width(8.dp))
            }
        }

        if (recentCardList.isEmpty()) {
            HomeEmptyComponent(
                text = "최근 결과가 올라온 공고가 없습니다.",
            )
        } else {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(20.dp)
            ) {
                items(
                    items = recentCardList.take(MAX_RECENT_CARD_COUNT),
                    key = { it.id },
                ) {
                    HapHapCard(
                        type = CardType.SMALL,
                        imageUrl = it.imageUrl,
                        text = it.text,
                        stage = it.stage,
                        dDay = it.dDay,
                        company = it.company,
                        description = it.description,
                        onCardClick = { onCardClick(it.id) },
                    )

                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeRecentCardSectionPreview() {
    HapHapTheme {
        var selectedChips by remember { mutableStateOf(persistentListOf(1)) }

        HomeRecentCardSection(
            chipList = DEFAULT_CHIP_LIST,
            selectedChips = selectedChips,
            onFilterClick = { id ->
                selectedChips = when {
                    id == 1 -> persistentListOf(1)
                    selectedChips.contains(id) -> {
                        if (selectedChips.size == 1) selectedChips
                        else selectedChips.remove(id)
                    }

                    else -> selectedChips.remove(1).add(id)
                }
            },

            recentCardList = persistentListOf(
                RecentCardModel(
                    id = 1,
                    imageUrl = "",
                    text = "개발",
                    stage = "서류",
                    dDay = 2,
                    company = "카카오",
                    description = "공고 설명",
                ),
                RecentCardModel(
                    id = 2,
                    imageUrl = "",
                    text = "개발",
                    stage = "서류",
                    dDay = 2,
                    company = "카카오",
                    description = "공고 설명",
                ),
                RecentCardModel(
                    id = 3,
                    imageUrl = "",
                    text = "개발",
                    stage = "서류",
                    dDay = 2,
                    company = "카카오",
                    description = "공고 설명",
                ),
                RecentCardModel(
                    id = 4,
                    imageUrl = "",
                    text = "개발",
                    stage = "서류",
                    dDay = 2,
                    company = "카카오",
                    description = "공고 설명",
                ),
            ),
            onCardClick = {},
        )
    }
}