package com.haphap.app.presentation.search.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.card.HapHapCard
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.CardType
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.data.model.search.RecentSearchItemModel
import com.haphap.app.data.model.search.SearchPopularItemModel
import com.haphap.app.presentation.search.SearchUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


@Composable
fun SearchDefaultSection(
    recentSearchUiState: SearchUiState,
    popularSearchUiState: SearchUiState,
    recentSearchList: ImmutableList<RecentSearchItemModel>,
    trendJobList: ImmutableList<SearchPopularItemModel>,
    onRecentItemClick: (String) -> Unit,
    onDeleteClick: (Long) -> Unit,
    onCardClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(vertical = 12.dp)
            .verticalScroll(rememberScrollState()),
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 20.dp),
        ) {
            Text(
                text = "최근 검색어",
                color = HapHapTheme.colors.gray400,
                style = HapHapTheme.typography.caption.m12,
            )

            Spacer(modifier = Modifier.height(8.dp))

            when (recentSearchUiState) {
                SearchUiState.Success -> {
                    recentSearchList.forEach { item ->
                        RecentSearchItem(
                            keyword = item.keyword,
                            date = item.date,
                            onRecentItemClick = { onRecentItemClick(item.keyword) },
                            onDeleteClick = { onDeleteClick(item.id) },
                        )

                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth(),
                            thickness = 1.dp,
                            color = HapHapTheme.colors.gray100,
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                SearchUiState.Empty -> {
                    IconEmptyComponent(
                        text = "최근 검색어가 없습니다",
                        modifier = Modifier.padding(top = 24.dp),
                    )
                }

                is SearchUiState.Failure, SearchUiState.Idle, SearchUiState.Loading -> {}
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "인기 공고",
                color = HapHapTheme.colors.gray400,
                style = HapHapTheme.typography.caption.m12,
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        when (popularSearchUiState) {
            SearchUiState.Success -> {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 20.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(
                        items = trendJobList,
                        key = { it.id }
                    ) {
                        HapHapCard(
                            type = CardType.BIG,
                            imageUrl = it.imageUrl,
                            text = it.category,
                            stage = it.nextStage,
                            dDay = it.dDay,
                            company = it.companyName,
                            description = it.title,
                            onCardClick = { onCardClick(it.id) },
                            modifier = Modifier.widthIn(max = 186.dp)
                        )
                    }
                }
            }

            SearchUiState.Empty -> {
                EmptyComponent(
                    text = "인기 공고가 없습니다",
                )
            }

            is SearchUiState.Failure, SearchUiState.Idle, SearchUiState.Loading -> {}

        }
    }
}


@Composable
private fun RecentSearchItem(
    keyword: String,
    date: String,
    onRecentItemClick: () -> Unit,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .padding(vertical = 2.dp)
            .noRippleClickable(onClick = onRecentItemClick),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = keyword,
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray700,
            modifier = Modifier.weight(1f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = date,
            color = HapHapTheme.colors.gray500,
            style = HapHapTheme.typography.caption.r12,
        )

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_delete_36),
            contentDescription = null,
            tint = HapHapTheme.colors.gray200,
            modifier = Modifier.noRippleClickable(onClick = onDeleteClick)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchDefaultSectionPreview() {
    HapHapTheme {
        SearchDefaultSection(
            recentSearchUiState = SearchUiState.Success,
            popularSearchUiState = SearchUiState.Success,
            recentSearchList = persistentListOf(
                RecentSearchItemModel(
                    id = 1,
                    keyword = "2026 신입 공개 채용",
                    date = "01.22"
                ),
                RecentSearchItemModel(
                    id = 2,
                    keyword = "2026 신입 공개 채용",
                    date = "01.22"
                ),
                RecentSearchItemModel(
                    id = 3,
                    keyword = "2026 신입 공개 채용 채용채용채용채용채용채용채용",
                    date = "01.22"
                ),
            ),
            trendJobList = persistentListOf(
                SearchPopularItemModel(
                    id = 1,
                    imageUrl = "",
                    category = "개발",
                    nextStage = "서류",
                    dDay = "D-2",
                    companyName = "카카오",
                    title = "공고명"
                ),
                SearchPopularItemModel(
                    id = 2,
                    imageUrl = "",
                    category = "개발",
                    nextStage = "서류",
                    dDay = "D-2",
                    companyName = "카카오",
                    title = "공고명"
                ),
                SearchPopularItemModel(
                    id = 3,
                    imageUrl = "",
                    category = "개발",
                    nextStage = "서류",
                    dDay = "D-2",
                    companyName = "카카오",
                    title = "공고명"
                )
            ),
            onRecentItemClick = {},
            onDeleteClick = {},
            onCardClick = {},
        )
    }
}
