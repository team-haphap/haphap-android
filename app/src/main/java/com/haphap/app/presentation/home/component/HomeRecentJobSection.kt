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
import com.haphap.app.data.model.home.RecentJobCardModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

private const val MAX_RECENT_CARD_COUNT = 10

@Composable
fun HomeRecentJobSection(
    recentJobCardList: ImmutableList<RecentJobCardModel>,
    onRecentCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        HomeCardTitle(
            title = "최근 내가 본 공고",
            description = "최근에 살펴본 공고를 모아봤어요",
            isMore = false,
        )

        Spacer(modifier = Modifier.height(14.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 20.dp)
        ) {
            items(
                items = recentJobCardList.take(MAX_RECENT_CARD_COUNT),
                key = { it.id },
            ) {
                HapHapCard(
                    type = CardType.BIG,
                    imageUrl = it.logoImageUrl,
                    text = it.position,
                    stage = it.nextStage.orEmpty(),
                    dDay = it.dDayLabel,
                    company = it.companyName,
                    description = it.title,
                    onCardClick = { onRecentCardClick(it.id) },
                    modifier = Modifier.width(155.dp)
                )

                Spacer(modifier = Modifier.width(5.dp))
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeRecentJobSectionPreview() {
    HapHapTheme {
        HomeRecentJobSection(
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
                    position = "개발/데이터",
                    nextStage = "서류",
                    dDayLabel = "D-2",
                    companyName = "카카오",
                ),
                RecentJobCardModel(
                    id = 3,
                    logoImageUrl = "",
                    title = "공고명",
                    position = "인사",
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
            onRecentCardClick = {},
        )
    }
}
