package com.haphap.app.presentation.search

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.search.RecentSearchItemModel
import com.haphap.app.data.model.search.RelatedKeywordItemModel
import com.haphap.app.data.model.search.SearchAutoCompleteItemModel
import com.haphap.app.data.model.search.SearchPopularItemModel
import com.haphap.app.data.model.search.SearchResultItemModel
import com.haphap.app.presentation.common.state.CategoryChipState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface SearchContract {
    @Immutable
    data class State(
        val searchAutoCompleteList: ImmutableList<SearchAutoCompleteItemModel> = persistentListOf(),
        val relatedKeywordList: ImmutableList<RelatedKeywordItemModel> = persistentListOf(),
        val categoryChipState: CategoryChipState = CategoryChipState(),
        val searchResultList: ImmutableList<SearchResultItemModel> = persistentListOf(),
        val recentSearchList: ImmutableList<RecentSearchItemModel> = persistentListOf(),
        val trendJobList: ImmutableList<SearchPopularItemModel> = persistentListOf(),
        val trendJobListUiState: SearchUiState = SearchUiState.Idle,
        val searchAutoCompleteUiState: SearchUiState = SearchUiState.Idle,
        val relatedKeywordListUiState: SearchUiState = SearchUiState.Idle,
        val searchResultListUiState: SearchUiState = SearchUiState.Idle,
        val searchUiState: SearchUiState = SearchUiState.Idle,

        val searchResultPage: Int = 0,
        val hasNextSearchResult: Boolean = true,
    ) {
        val section: SearchSection
            get() = when {
                searchAutoCompleteUiState !is SearchUiState.Idle -> SearchSection.Searching
                searchResultListUiState !is SearchUiState.Idle -> SearchSection.Result
                else -> SearchSection.Default
            }

    }

    sealed class SideEffect {
        data class OnShowToast(
            val message: String,
            val isAlarm: Boolean = false,
        ) : SideEffect()
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
