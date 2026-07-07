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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.card.HapHapCard
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.CardType
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.data.model.search.RecentSearchListModel
import com.haphap.app.data.model.search.TrendJobListModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf


@Composable
fun SearchDefaultSection(
    recentSearchList: ImmutableList<RecentSearchListModel>,
    trendJobList: ImmutableList<TrendJobListModel>,
    onDeleteClick: () -> Unit,
    onCardClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(top = 12.dp),
    ) {
        Column(
            modifier = modifier.padding(horizontal = 20.dp),
        ) {
            Text(
                text = "최근 검색어",
                color = HapHapTheme.colors.gray400,
                style = HapHapTheme.typography.caption.m12,
            )

            Spacer(modifier = Modifier.height(8.dp))

            recentSearchList.forEach {
                RecentSearchItem(
                    keyword = it.keyword,
                    date = it.date,
                    onDeleteClick = onDeleteClick,
                )

                HorizontalDivider(
                    modifier = Modifier.fillMaxWidth(),
                    thickness = 1.dp,
                    color = HapHapTheme.colors.gray100,
                )

                Spacer(modifier = Modifier.height(8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "인기 공고",
                color = HapHapTheme.colors.gray400,
                style = HapHapTheme.typography.caption.m12,
            )

            Spacer(modifier = Modifier.height(12.dp))

        }

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
                    text = it.text,
                    stage = it.stage,
                    dDay = it.dDay,
                    company = it.company,
                    description = it.description,
                    onCardClick = onCardClick,
                    modifier = Modifier.widthIn(max = 186.dp)
                )
            }
        }
    }
}


@Composable
private fun RecentSearchItem(
    keyword: String,
    date: String,
    onDeleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = keyword,
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray700,
        )

        Spacer(modifier = Modifier.weight(1f))

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
            recentSearchList = persistentListOf(
                RecentSearchListModel(
                    id = 1,
                    keyword = "2026 신입 공개 채용",
                    date = "01.22"
                ),
                RecentSearchListModel(
                    id = 2,
                    keyword = "2026 신입 공개 채용",
                    date = "01.22"
                ),
                RecentSearchListModel(
                    id = 3,
                    keyword = "2026 신입 공개 채용",
                    date = "01.22"
                ),
            ),
            trendJobList = persistentListOf(
                TrendJobListModel(
                    id = 1,
                    imageUrl = "",
                    text = "개발",
                    stage = "서류",
                    dDay = 2,
                    company = "카카오",
                    description = "공고명"
                ),
                TrendJobListModel(
                    id = 2,
                    imageUrl = "",
                    text = "개발",
                    stage = "서류",
                    dDay = 2,
                    company = "카카오",
                    description = "공고명"
                ),
                TrendJobListModel(
                    id = 3,
                    imageUrl = "",
                    text = "개발",
                    stage = "서류",
                    dDay = 2,
                    company = "카카오",
                    description = "공고명"
                )
            ),
            onDeleteClick = {},
            onCardClick = {},
        )
    }
}
