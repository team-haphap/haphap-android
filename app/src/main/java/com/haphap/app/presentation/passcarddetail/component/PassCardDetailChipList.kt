package com.haphap.app.presentation.passcarddetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.chip.FilterChipContent
import com.haphap.app.core.designsystem.component.chip.HapHapFilterChip
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.passcard.PassCardDetailChipListModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PassCardDetailChipList(
    chipList: ImmutableList<PassCardDetailChipListModel>,
    selectedChip: Int,
    onStageClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = chipList,
            key = { it.id },
        ) {
            HapHapFilterChip(
                content = FilterChipContent.TextContent(it.stageName),
                onFilterClick = {
                    if (it.id != selectedChip) onStageClick(it.id)
                },
                isFilterSelected = it.id == selectedChip, //Todo: 추후 api 연결하면서 수정
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 360)
@Composable
private fun PassCardDetailChipListPreview() {
    HapHapTheme {
        var selectedChip by remember { mutableIntStateOf(101) }

        PassCardDetailChipList(
            chipList = persistentListOf(
                PassCardDetailChipListModel(id = 101, stageName = "서류"),
                PassCardDetailChipListModel(id = 102, stageName = "인적성"),
                PassCardDetailChipListModel(id = 103, stageName = "코딩테스트"),
                PassCardDetailChipListModel(id = 104, stageName = "1차 면접 테스트"),
                PassCardDetailChipListModel(id = 105, stageName = "최종"),
            ),
            selectedChip = selectedChip,
            onStageClick = { selectedChip = it },
        )
    }
}