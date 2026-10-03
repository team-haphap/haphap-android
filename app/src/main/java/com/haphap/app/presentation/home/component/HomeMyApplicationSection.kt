package com.haphap.app.presentation.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.data.model.home.MyApplicationCardModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HomeMyApplicationSection(
    myApplicationCardList: ImmutableList<MyApplicationCardModel>,
    onListCardClick: (Int) -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(HapHapTheme.colors.sub100),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Row(
                modifier = Modifier.padding(all = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "내 지원을 확인해보세요",
                    style = HapHapTheme.typography.body.sb18,
                    color = HapHapTheme.colors.gray700,
                )

                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = "전체보기",
                    style = HapHapTheme.typography.caption.m12,
                    color = HapHapTheme.colors.gray300,
                    modifier = Modifier.noRippleClickable(onClick = onMoreClick)
                )
            }

            myApplicationCardList
                .take(3)
                .forEach {
                    HomeMyApplicationCard(
                        imageUrl = it.logoImageUrl,
                        companyName = it.companyName,
                        title = it.title,
                        category = it.category.orEmpty(),
                        stageName = it.currentStageStatus,
                        dDay = it.dDayLabel,
                        onCardClick = { onListCardClick(it.id) },
                    )
                }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun HomeMyApplicationSectionPreview() {
    HapHapTheme {
        HomeMyApplicationSection(
            myApplicationCardList = persistentListOf(
                MyApplicationCardModel(
                    id = 1,
                    logoImageUrl = "",
                    companyName = "카카오",
                    title = "",
                    category = "개발/데이터",
                    currentStageStatus = "서류 전형",
                    dDayLabel = "D-3",
                ),
                MyApplicationCardModel(
                    id = 2,
                    logoImageUrl = "",
                    companyName = "네이버",
                    title = "",
                    category = "개발/데이터",
                    currentStageStatus = "코딩테스트",
                    dDayLabel = "D-5",
                ),
                MyApplicationCardModel(
                    id = 3,
                    logoImageUrl = "",
                    companyName = "토스",
                    title = "",
                    category = "개발/데이터",
                    currentStageStatus = "면접 전형",
                    dDayLabel = "D-7",
                ),
            ),
            onListCardClick = {},
            onMoreClick = {},
        )
    }
}
