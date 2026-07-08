package com.haphap.app.presentation.search

import androidx.compose.runtime.Immutable
import com.haphap.app.core.designsystem.component.chip.CategoryChipState
import com.haphap.app.data.model.search.RecentSearchListModel
import com.haphap.app.data.model.search.RelatedKeywordListModel
import com.haphap.app.data.model.search.SearchAutoCompleteModel
import com.haphap.app.data.model.search.SearchResultModel
import com.haphap.app.data.model.search.TrendJobListModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface SearchContract {
    @Immutable
    data class State(
        val searchAutoCompleteList: ImmutableList<SearchAutoCompleteModel> = persistentListOf(),
        val relatedKeywordList: ImmutableList<RelatedKeywordListModel> = persistentListOf(),
        val categoryChipState: CategoryChipState = CategoryChipState(),
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
            category: String,
        ): State =
            copy(categoryChipState = categoryChipState.toggle(category))
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
