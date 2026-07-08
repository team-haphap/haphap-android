package com.haphap.app.presentation.jobdetail.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun JobResultTabRow(
    stages: ImmutableList<String>,
    selectedStage: String,
    onStageClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyRow(
        modifier = modifier.padding(horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(
            items = stages,
            key = { it }
        ) { stage ->
            HapHapFilterChip(
                content = FilterChipContent.TextContent(stage),
                isFilterSelected = stage == selectedStage,
                onFilterClick = { onStageClick(stage) },
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JobResultTabRowPreview() {
    HapHapTheme {
        var selectedStage by remember { mutableStateOf("서류") }
        JobResultTabRow(
            stages = persistentListOf("서류", "인적성", "코딩테스트", "1차면접", "2차면접"),
            selectedStage = selectedStage,
            onStageClick = { selectedStage = it },
        )
    }
}