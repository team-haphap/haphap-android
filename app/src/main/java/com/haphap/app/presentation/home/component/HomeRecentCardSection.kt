package com.haphap.app.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.haphap.app.data.model.home.RecentCardModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private const val MAX_RECENT_CARD_COUNT = 8

@Composable
fun HomeRecentCardSection(
    recentCardList: ImmutableList<RecentCardModel>,
    onRecentCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (recentCardList.isEmpty()) {

            HomeEmptyComponent(
                text = "최근 결과가 올라온 공고가 없습니다.",
            )
        } else {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                items(
                    items = recentCardList.take(MAX_RECENT_CARD_COUNT),
                    key = { it.id },
                ) {
                    HapHapCard(
                        type = CardType.BIG,
                        imageUrl = it.imageUrl,
                        text = it.category,
                        stage = it.nextStage,
                        dDay = it.dayUntilNextStage,
                        company = it.companyName,
                        description = it.title,
                        onCardClick = { onRecentCardClick(it.id) },
                        modifier = Modifier.width(186.dp)
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
        HomeRecentCardSection(
            recentCardList = persistentListOf(
                RecentCardModel(
                    id = 1,
                    imageUrl = "",
                    title = "공고명",
                    category = "개발/데이터",
                    nextStage = "서류",
                    dayUntilNextStage = "D-2",
                    companyName = "카카오",
                ),
                RecentCardModel(
                    id = 2,
                    imageUrl = "",
                    title = "공고명",
                    category = "개발/데이터",
                    nextStage = "서류",
                    dayUntilNextStage = "D-2",
                    companyName = "카카오",
                ),
                RecentCardModel(
                    id = 3,
                    imageUrl = "",
                    title = "공고명",
                    category = "인사",
                    nextStage = "서류",
                    dayUntilNextStage = "D-2",
                    companyName = "카카오",
                ),
                RecentCardModel(
                    id = 4,
                    imageUrl = "",
                    title = "공고명",
                    category = "인사",
                    nextStage = "서류",
                    dayUntilNextStage = "D-2",
                    companyName = "카카오",
                ),
            ),
            onRecentCardClick = {},
        )
    }
}
