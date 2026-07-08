package com.haphap.app.core.designsystem.component.chip

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.search.ChipListModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun HapHapCategoryChipList(
    chipList: ImmutableList<ChipListModel>,
    selectedChips: ImmutableList<String>,
    onFilterClick: (String) -> Unit,
    modifier: Modifier = Modifier,
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
                onFilterClick = {},
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


@Immutable
data class CategoryChipState(
    val chipList: ImmutableList<ChipListModel> = DEFAULT_CHIP_LIST,
    val selectedChips: PersistentList<String> = persistentListOf("전체"),
) {
    fun toggle(category: String): CategoryChipState =
        copy(
            selectedChips = when {
                category == "전체" -> persistentListOf("전체")
                selectedChips.size == 1 && selectedChips.contains(category) -> selectedChips
                selectedChips.contains(category) -> selectedChips.remove(category)
                else -> selectedChips.remove("전체").add(category)
            }
        )

    companion object {
        val DEFAULT_CHIP_LIST: ImmutableList<ChipListModel> = persistentListOf(
            ChipListModel(id = 1, category = "전체"),
            ChipListModel(id = 2, category = "기획"),
            ChipListModel(id = 3, category = "마케팅/홍보"),
            ChipListModel(id = 4, category = "인사"),
            ChipListModel(id = 5, category = "영업"),
            ChipListModel(id = 6, category = "개발/데이터"),
            ChipListModel(id = 7, category = "금융/보험"),
        )
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
