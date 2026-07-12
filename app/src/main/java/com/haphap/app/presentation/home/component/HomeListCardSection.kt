package com.haphap.app.presentation.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.home.TodayExpectedCardModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomeListCardSection(
    todayExpectedCardList: ImmutableList<TodayExpectedCardModel>,
    onListCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (todayExpectedCardList.isEmpty()) {
            HomeEmptyComponent(
                text = "오늘 발표 예상 공고가 없습니다.",
            )
        } else {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                todayExpectedCardList
                    .take(3)
                    .forEach {
                        HomeListCardComponent(
                            imageUrl = it.imageUrl,
                            title = it.title,
                            companyName = it.companyName,
                            category = it.category,
                            stageName = it.stageName,
                            onCardClick = { onListCardClick(it.id) },
                        )
                    }
            }

            Spacer(modifier = Modifier.height(52.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeListCardSectionPreview() {
    HapHapTheme {
        HomeListCardSection(
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
            onListCardClick = {},
        )
    }
}