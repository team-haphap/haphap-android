package com.haphap.app.presentation.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.core.designsystem.component.chip.FilterChipContent
import com.haphap.app.core.designsystem.component.chip.HapHapFilterChip
import com.haphap.app.core.designsystem.component.empty.RegisterEmpty
import com.haphap.app.core.designsystem.component.topbar.HapHapTextTopBar
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.myapplication.MyApplicationModel
import com.haphap.app.presentation.myapplication.component.MyApplicationCardItem
import com.haphap.app.presentation.myapplication.type.MyApplicationFilterType
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MyApplicationRoute(
    navigateBack: () -> Unit,
    navigateToJobDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyApplicationViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    MyApplicationScreen(
        uiState = uiState,
        onBackClick = navigateBack,
        onFilterClick = viewModel::onFilterClick,
        onApplicationClick = navigateToJobDetail,
        modifier = modifier,
    )
}

@Composable
private fun MyApplicationScreen(
    uiState: MyApplicationContract.State,
    onBackClick: () -> Unit,
    onFilterClick: (MyApplicationFilterType) -> Unit,
    onApplicationClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = HapHapTheme.colors.white)
            .padding(horizontal = 20.dp),
    ) {
        HapHapTextTopBar(
            text = "내 지원 내역",
            onBackClick = onBackClick,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = "결과가 반영된 공고를 모아봤어요.\n전형은 가장 최신 결과로 업데이트돼요.",
            style = HapHapTheme.typography.body.sb13,
            color = HapHapTheme.colors.gray400,
        )

        Spacer(modifier = Modifier.height(18.dp))

        Row(
            modifier = Modifier
                .horizontalScroll(rememberScrollState())
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            MyApplicationFilterType.entries.forEach { filter ->
                HapHapFilterChip(
                    content = FilterChipContent.TextContent(uiState.filterLabel(filter)),
                    onFilterClick = { onFilterClick(filter) },
                    isFilterSelected = uiState.selectedFilter == filter,
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        if (uiState.applicationList.isEmpty()) {
            Spacer(modifier = Modifier.weight(197f))

            RegisterEmpty(
                title = "지원한 내역이 없어요.",
                description = "를 통해 지원 내역을 등록해보세요",
            )

            Spacer(modifier = Modifier.weight(318f))
        } else {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 6.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
            ) {
                items(
                    items = uiState.applicationList,
                    key = { it.postingId },
                ) {
                    MyApplicationCardItem(
                        logoImageUrl = it.logoImageUrl,
                        company = it.companyName,
                        title = it.title,
                        currentStage = it.currentStage,
                        isClosed = it.isClosed,
                        result = it.result,
                        onClick = { onApplicationClick(it.postingId) },
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MyApplicationScreenPreview() {
    HapHapTheme {
        MyApplicationScreen(
            uiState = MyApplicationContract.State(
                applicationList = persistentListOf(
                    MyApplicationModel(
                        postingId = 1,
                        companyName = "카카오",
                        title = "2026 신입 개발자 공개채용 2026 신입 개발자 공개채용 2026 신입 개발자 공개채용 2026 신입 개발자 공개채용",
                        logoImageUrl = "",
                        currentStage = "마감",
                        isClosed = true,
                        result = "서류 합격",
                    ),
                    MyApplicationModel(
                        postingId = 2,
                        companyName = "카카오",
                        title = "2026 신입 개발자 공개채용",
                        logoImageUrl = "",
                        currentStage = "서류",
                        isClosed = false,
                        result = "서류 불합격",
                    ),
                    MyApplicationModel(
                        postingId = 3,
                        companyName = "카카오",
                        title = "2026 신입 개발자 공개채용",
                        logoImageUrl = "",
                        currentStage = "1차 면접",
                        isClosed = false,
                        result = "1차 면접 아직 몰라요",
                    ),
                ),
            ),
            onBackClick = {},
            onFilterClick = {},
            onApplicationClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyApplicationScreenEmptyPreview() {
    HapHapTheme {
        MyApplicationScreen(
            uiState = MyApplicationContract.State(),
            onBackClick = {},
            onFilterClick = {},
            onApplicationClick = {},
        )
    }
}
