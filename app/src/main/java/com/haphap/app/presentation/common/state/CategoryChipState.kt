package com.haphap.app.presentation.common.state

import androidx.compose.runtime.Immutable
import com.haphap.app.presentation.common.model.ChipListModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

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
