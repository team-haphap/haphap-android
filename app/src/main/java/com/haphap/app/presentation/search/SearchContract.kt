package com.haphap.app.presentation.search

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.search.ChipListModel
import com.haphap.app.data.model.search.RecentSearchListModel
import com.haphap.app.data.model.search.RelatedKeywordListModel
import com.haphap.app.data.model.search.SearchAutoCompleteModel
import com.haphap.app.data.model.search.SearchResultModel
import com.haphap.app.data.model.search.TrendJobListModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

sealed interface SearchContract {
    @Immutable
    data class State(
        val searchAutoCompleteList: ImmutableList<SearchAutoCompleteModel> = persistentListOf(),
        val relatedKeywordList: ImmutableList<RelatedKeywordListModel> = persistentListOf(),
        val chipList: ImmutableList<ChipListModel> = DEFAULT_CHIP_LIST,
        val selectedChips: PersistentList<Int> = persistentListOf(1),
        val searchResultList: ImmutableList<SearchResultModel> = persistentListOf(),
        val recentSearchList: ImmutableList<RecentSearchListModel> = persistentListOf(),
        val trendJobList: ImmutableList<TrendJobListModel> = persistentListOf(),
        val trendJobListUiState: SearchUiState = SearchUiState.Idle,
        val searchAutoCompleteUiState: SearchUiState = SearchUiState.Idle,
        val relatedKeywordListUiState: SearchUiState = SearchUiState.Idle,
        val searchResultListUiState: SearchUiState = SearchUiState.Idle,
        val searchUiState: SearchUiState = SearchUiState.Idle,
    ) {
        val section: SearchSection
            get() = when {
                searchAutoCompleteUiState is SearchUiState.Success
                        || searchAutoCompleteUiState is SearchUiState.Empty -> SearchSection.Searching

                searchResultListUiState is SearchUiState.Success
                        || searchResultListUiState is SearchUiState.Empty -> SearchSection.Result

                else -> SearchSection.Default
            }

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

sealed interface SearchSection {
    data object Default : SearchSection
    data object Searching : SearchSection
    data object Result : SearchSection
}

sealed interface SearchUiState {
    data object Idle : SearchUiState
    data object Loading : SearchUiState
    data object Empty : SearchUiState
    data object Success : SearchUiState
    data class Failure(
        val msg: String,
    ) : SearchUiState
}
