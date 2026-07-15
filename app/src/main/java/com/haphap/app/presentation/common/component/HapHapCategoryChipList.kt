package com.haphap.app.presentation.common.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.chip.FilterChipContent
import com.haphap.app.core.designsystem.component.chip.HapHapFilterChip
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.common.model.ChipListModel
import com.haphap.app.presentation.common.state.CategoryChipState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun HapHapCategoryChipList(
    chipList: ImmutableList<ChipListModel>,
    selectedChips: ImmutableList<String>,
    onFilterClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    onIconFilterClick: () -> Unit = {},
) {
    LazyRow(
        modifier = modifier.fillMaxWidth(),
        contentPadding = PaddingValues(vertical = 2.dp, horizontal = 20.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            HapHapFilterChip(
                content = FilterChipContent.IconContent(
                    iconRes = R.drawable.ic_filter_20,
                ),
                onFilterClick = onIconFilterClick,
                isFilterSelected = false
            )
        }

        items(
            items = chipList,
            key = { it.id },
        ) {
            HapHapFilterChip(
                content = FilterChipContent.TextContent(it.category),
                onFilterClick = { onFilterClick(it.category) },
                isFilterSelected = selectedChips.contains(it.category),
            )
        }
    }
}

@Preview
@Composable
private fun HapHapCategoryChipListPreview() {
    HapHapTheme {
        var categoryChipState by remember { mutableStateOf(CategoryChipState()) }

        HapHapCategoryChipList(
            chipList = categoryChipState.chipList,
            selectedChips = categoryChipState.selectedChips,
            onFilterClick = { category ->
                categoryChipState = categoryChipState.toggle(category)
            },
        )
    }
}
