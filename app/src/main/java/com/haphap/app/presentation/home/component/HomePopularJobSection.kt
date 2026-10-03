package com.haphap.app.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.card.HapHapCard
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.CardType
import com.haphap.app.data.model.home.PopularJobCardModel
import com.haphap.app.presentation.common.component.HapHapCategoryChipList
import com.haphap.app.presentation.common.state.CategoryChipState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private const val MAX_RECENT_CARD_COUNT = 10

@Composable
fun HomePopularJobSection(
    popularJobCardList: ImmutableList<PopularJobCardModel>,
    categoryChipState: CategoryChipState,
    onFilterClick: (String) -> Unit,
    onPopularCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HomeCardTitle(
            title = "다른 사용자들이 많이 보는 공고",
            description = "다른 사용자들의 관심 공고를 확인해보세요",
            isMore = false,
        )

        Spacer(modifier = Modifier.height(12.dp))

        HapHapCategoryChipList(
            chipList = categoryChipState.chipList,
            selectedChips = categoryChipState.selectedChips,
            onFilterClick = onFilterClick,
        )

        Spacer(modifier = Modifier.height(14.dp))

        if (popularJobCardList.isEmpty()) {

            HomeEmptyComponent(
                text = "최근 결과가 올라온 공고가 없습니다",
            )
        } else {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(
                    items = popularJobCardList.take(MAX_RECENT_CARD_COUNT),
                    key = { it.id },
                ) {
                    HapHapCard(
                        type = CardType.BIG,
                        imageUrl = it.logoImageUrl,
                        text = it.category.orEmpty(),
                        stage = it.nextStage.orEmpty(),
                        dDay = it.dDayLabel,
                        company = it.companyName,
                        description = it.title,
                        onCardClick = { onPopularCardClick(it.id) },
                        modifier = Modifier.width(155.dp)
                    )

                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HomePopularJobSectionPreview() {
    HapHapTheme {
        HomePopularJobSection(
            popularJobCardList = persistentListOf(
                PopularJobCardModel(
                    id = 1,
                    logoImageUrl = "",
                    title = "공고명",
                    category = "개발/데이터",
                    nextStage = "서류",
                    dDayLabel = "D-2",
                    companyName = "카카오",
                ),
                PopularJobCardModel(
                    id = 2,
                    logoImageUrl = "",
                    title = "공고명",
                    category = "개발/데이터",
                    nextStage = "서류",
                    dDayLabel = "D-2",
                    companyName = "카카오",
                ),
                PopularJobCardModel(
                    id = 3,
                    logoImageUrl = "",
                    title = "공고명",
                    category = "인사",
                    nextStage = "서류",
                    dDayLabel = "D-2",
                    companyName = "카카오",
                ),
                PopularJobCardModel(
                    id = 4,
                    logoImageUrl = "",
                    title = "공고명",
                    category = "인사",
                    nextStage = "서류",
                    dDayLabel = "D-2",
                    companyName = "카카오",
                ),
            ),
            categoryChipState = CategoryChipState(),
            onFilterClick = {},
            onPopularCardClick = {},
        )
    }
}
