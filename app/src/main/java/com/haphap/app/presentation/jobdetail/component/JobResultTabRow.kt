package com.haphap.app.presentation.jobdetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.chip.FilterChipContent
import com.haphap.app.core.designsystem.component.chip.HapHapFilterChip
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.detail.JobResultTabModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun JobResultTabRow(
    stages: ImmutableList<JobResultTabModel>,
    selectedStage: Int,
    onStageClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        contentPadding = PaddingValues(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        itemsIndexed(
            items = stages,
            key = { _, stage -> stage.stageId },
        ) { index, stage ->
            HapHapFilterChip(
                content = FilterChipContent.TextContent(stage.stageName),
                isFilterSelected = index == selectedStage,
                onFilterClick = { onStageClick(index) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JobResultTabRowPreview() {
    HapHapTheme {
        var selectedStageIndex by remember { mutableStateOf(0) }
        JobResultTabRow(
            stages = persistentListOf(
                JobResultTabModel(1, "서류"),
                JobResultTabModel(2, "인적성"),
                JobResultTabModel(3, "코딩테스트"),
                JobResultTabModel(4, "1차면접"),
                JobResultTabModel(5, "2차면접"),
            ),
            selectedStage = selectedStageIndex,
            onStageClick = { selectedStageIndex = it },
        )
    }
}
