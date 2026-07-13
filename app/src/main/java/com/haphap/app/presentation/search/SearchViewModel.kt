package com.haphap.app.presentation.search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil3.util.CoilUtils.result
import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.repository.api.SearchRepository
import com.haphap.app.presentation.search.SearchContract.SideEffect.OnShowToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<SearchContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    val searchInputState = TextFieldState()

    init {
        observeSearchInput()
        getRecentSearchList()
        getPopularList()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchInput() = viewModelScope.launch {
        snapshotFlow { searchInputState.text }
            .debounce(SEARCH_NETWORK_DEBOUNCE)
            .distinctUntilChanged()
            .collectLatest { searchInputText ->
                if (searchInputText.isBlank()) {
                    _uiState.update {
                        it.copy(
                            searchAutoCompleteUiState = SearchUiState.Idle,
                            searchResultListUiState = SearchUiState.Idle,
                        )
                    }
                } else {
                    getSearchingList(searchInputState.text.toString())
                }
            }
    }

    fun updateSelectedChips(category: String) {
        _uiState.update {
            it.copy(categoryChipState = it.categoryChipState.toggle(category))
        }
        getSearchResultList()
    }

    fun onSearchClick() = viewModelScope.launch {
        if (searchInputState.text.isBlank()) {
            _sideEffect.send(OnShowToast("검색어를 입력해주세요"))
        } else {
            searchRepository.saveRecentSearchItem(searchInputState.text.toString())
                .onSuccess {
                    Timber.d("저장 성공했습니다.")
                }
                .onFailure {
                    Timber.e("$it 저장 실패했습니다.")

                }
            _uiState.update {
                it.copy(
                    searchAutoCompleteUiState = SearchUiState.Idle,
                    searchResultListUiState = SearchUiState.Loading,
                )
            }
            getSearchResultList()
        }
    }

    fun getRecentSearchList() = viewModelScope.launch {
        suspendRunCatching {
            searchRepository.getRecentSearchItem().collect { list ->
                _uiState.update { it.copy(recentSearchList = list.toImmutableList()) }
            }
        }.onFailure {
            Timber.e("$it 불러오기 실패했습니다.")
        }
    }

    fun onRecentItemClick(keyword: String) {
        searchInputState.setTextAndPlaceCursorAtEnd(keyword)
        getSearchResultList()
    }


    fun getPopularList() = viewModelScope.launch {
        _uiState.update { it.copy(trendJobListUiState = SearchUiState.Loading) }
        searchRepository.getPopularList()
            .onSuccess { result ->
                _uiState.update {
                    it.copy(
                        trendJobList = result.toImmutableList(),
                        trendJobListUiState = SearchUiState.Success
                    )
                }
            }
            .onFailure { error ->
                Timber.e("인기 공고 리스트를 불러오지 못했습니다. $error")
                _uiState.update {
                    it.copy(
                        trendJobListUiState = SearchUiState.Failure("$error")
                    )
                }
            }
    }

    fun deleteRecentSearchItem(id: Long) = viewModelScope.launch {
        searchRepository.deleteRecentSearchItem(id)
            .onSuccess {
                Timber.d("삭제 성공했습니다.")
            }
            .onFailure {
                Timber.e("$it 삭제 실패했습니다.")

            }
    }

    fun getSearchingList(q: String?) = viewModelScope.launch {
        _uiState.update {
            it.copy(
                searchAutoCompleteUiState = SearchUiState.Loading,
                relatedKeywordListUiState = SearchUiState.Loading,
            )
        }
        searchRepository.getSearchingList(q = q)
            .onSuccess { result ->
                _uiState.update {
                    it.copy(
                        searchAutoCompleteList = result.relatedPostings,
                        relatedKeywordList = result.relatedKeywords,
                        searchAutoCompleteUiState = SearchUiState.Success,
                        relatedKeywordListUiState = SearchUiState.Success,
                    )
                }
            }
            .onFailure { error ->
                Timber.e("$error 자동 완성 호출에 실패했습니다")
                _uiState.update { result ->
                    result.copy(
                        searchAutoCompleteUiState = SearchUiState.Failure("$error"),
                        relatedKeywordListUiState = SearchUiState.Failure("$error"),
                    )
                }
            }
    }

    fun getSearchResultList(hasNextPage: Boolean = false) = viewModelScope.launch {
        val currentState = uiState.value
        if (hasNextPage && !currentState.hasNextSearchResult) return@launch

        val requestPage = if (hasNextPage) currentState.searchResultPage + 1 else 0

        _uiState.update { it.copy(searchResultListUiState = SearchUiState.Loading) }
        val category = currentState.categoryChipState.queryCategoryList
        searchRepository.getSearchResultList(
            q = searchInputState.text.toString(),
            category = category,
            page = requestPage,
            size = DEFAULT_PAGE_SIZE,
        )
            .onSuccess { result ->
                _uiState.update {
                    it.copy(
                        searchResultList = if (hasNextPage) {
                            (it.searchResultList + result.results).toImmutableList()
                        } else {
                            result.results
                        },
                        searchResultListUiState = SearchUiState.Success,
                        searchResultPage = result.page,
                        hasNextSearchResult = result.hasNext,
                    )
                }
            }
            .onFailure { error ->
                Timber.e("검색 결과 리스트를 불러오지 못했습니다. $error")
                _uiState.update {
                    it.copy(
                        searchResultListUiState = SearchUiState.Failure("$error")
                    )
                }
            }
    }


    companion object {
        private const val SEARCH_NETWORK_DEBOUNCE = 500L
        private const val DEFAULT_PAGE_SIZE = 20
    }

}
