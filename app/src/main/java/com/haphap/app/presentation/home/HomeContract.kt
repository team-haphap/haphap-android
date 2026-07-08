package com.haphap.app.presentation.home

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.home.BannerListModel
import com.haphap.app.data.model.home.ChipListModel
import com.haphap.app.data.model.home.CountCardModel
import com.haphap.app.data.model.home.RecentCardModel
import com.haphap.app.data.model.home.TodayExpectedCardModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

sealed interface HomeContract {
    @Immutable
    data class State(
        val bannerList: ImmutableList<BannerListModel> = persistentListOf(),
        val countCardModel: CountCardModel? = null,
        val chipList: ImmutableList<ChipListModel> = DEFAULT_CHIP_LIST,
        val selectedChips: PersistentList<Int> = persistentListOf(1),
        val recentCardList: ImmutableList<RecentCardModel> = persistentListOf(),
        val todayExpectedCardList: ImmutableList<TodayExpectedCardModel> = persistentListOf(),
    ) {
        fun toggleCategoryChips(
            id: Int,
        ): State =
            copy(
                selectedChips = when {
                    id == 1 -> persistentListOf(1)
                    selectedChips.size == 1 && selectedChips.contains(id) -> selectedChips
                    selectedChips.contains(id) -> selectedChips.remove(id)
                    else -> selectedChips.remove(1).add(id)
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
}