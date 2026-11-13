package com.haphap.app.presentation.search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.repository.api.search.SearchRepository
import com.haphap.app.presentation.search.SearchContract.SideEffect
import com.haphap.app.presentation.search.SearchContract.SideEffect.OnShowToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
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
import kotlin.time.Duration.Companion.milliseconds

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    val searchInputState = TextFieldState()

    private var executingSearchKeyword: String? = null

    init {
        observeSearchInput()
        getRecentSearchList()
        getPopularKeywordList()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchInput() = viewModelScope.launch {
        snapshotFlow { searchInputState.text }
            .debounce(SEARCH_NETWORK_DEBOUNCE.milliseconds)
            .distinctUntilChanged()
            .collectLatest { searchInputText ->
                val text = searchInputText.toString()
                when {
                    text.isBlank() -> {
                        _uiState.update {
                            it.copy(
                                searchAutoCompleteList = persistentListOf(),
                                relatedKeywordList = persistentListOf(),
                                searchAutoCompleteUiState = SearchUiState.Idle,
                                relatedKeywordListUiState = SearchUiState.Idle,
                                searchResultListUiState = SearchUiState.Idle,
                                storedSearchText = null,
                            )
                        }
                    }

                    text != _uiState.value.storedSearchText -> {
                        _uiState.update { it.copy(searchResultListUiState = SearchUiState.Idle) }
                        getSearchingList(text)
                    }
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
            val keyword = searchInputState.text.toString()
            if (keyword == executingSearchKeyword) return@launch
            executingSearchKeyword = keyword

            searchRepository.saveRecentSearchItem(keyword)
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
                    storedSearchText = keyword,
                )
            }
            getSearchResultList(keyword = keyword, isNewSearch = true)
        }
    }

    fun getRecentSearchList() = viewModelScope.launch {
        suspendRunCatching {
            searchRepository.getRecentSearchItem().collect { list ->
                _uiState.update {
                    it.copy(
                        recentSearchList = list.toImmutableList(),
                        recentSearchListUiState = if (list.isEmpty()) {
                            SearchUiState.Empty
                        } else {
                            SearchUiState.Success
                        },
                    )
                }
            }
        }.onFailure {
            Timber.e("$it 불러오기 실패했습니다.")
        }
    }

    fun onSearchItemClick(keyword: String) {
        if (keyword == executingSearchKeyword) return
        executingSearchKeyword = keyword

        searchInputState.setTextAndPlaceCursorAtEnd(keyword)
        _uiState.update {
            it.copy(
                searchAutoCompleteUiState = SearchUiState.Idle,
                storedSearchText = keyword,
            )
        }
        getSearchResultList(isNewSearch = true)
    }


    private fun getPopularKeywordList() = viewModelScope.launch {
        _uiState.update { it.copy(popularSearchListUiState = SearchUiState.Loading) }
        searchRepository.getPopularKeywordList()
            .onSuccess { result ->
                _uiState.update {
                    it.copy(
                        popularSearchList = result.toImmutableList(),
                        popularSearchListUiState = if (result.isEmpty()) {
                            SearchUiState.Empty
                        } else {
                            SearchUiState.Success
                        },
                    )
                }
            }
            .onFailure { error ->
                Timber.e("인기 실시간 검색어 리스트를 불러오지 못했습니다. $error")
                _uiState.update {
                    it.copy(
                        popularSearchListUiState = SearchUiState.Failure("$error")
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

    private suspend fun getSearchingList(q: String) {
        _uiState.update {
            it.copy(
                searchAutoCompleteUiState = SearchUiState.Loading,
                relatedKeywordListUiState = SearchUiState.Loading,
            )
        }
        searchRepository.getSearchingList(q = q)
            .onSuccess { result ->
                val isEmpty = result.relatedPostings.isEmpty() && result.relatedKeywords.isEmpty()
                _uiState.update {
                    it.copy(
                        searchAutoCompleteList = result.relatedPostings,
                        relatedKeywordList = result.relatedKeywords,
                        searchAutoCompleteUiState = if (isEmpty) SearchUiState.Empty else SearchUiState.Success,
                        relatedKeywordListUiState = if (isEmpty) SearchUiState.Empty else SearchUiState.Success,
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

    fun getSearchResultList(
        keyword: String = searchInputState.text.toString(),
        hasNextPage: Boolean = false,
        isNewSearch: Boolean = false,
    ) = viewModelScope.launch {
        val currentState = uiState.value
        if (hasNextPage && !currentState.hasNextSearchResult) return@launch

        val requestPage = if (hasNextPage) currentState.searchResultPage + 1 else 0

        _uiState.update { it.copy(searchResultListUiState = SearchUiState.Loading) }
        val category = currentState.categoryChipState.queryCategoryList
        searchRepository.getSearchResultList(
            q = keyword,
            category = category,
            page = requestPage,
            size = DEFAULT_PAGE_SIZE,
        )
            .onSuccess { result ->
                if (isNewSearch) postSearchExecution(keyword)
                _uiState.update {
                    val resultList = if (hasNextPage) {
                        it.searchResultList + result.results
                    } else {
                        result.results
                    }
                    it.copy(
                        searchResultList = resultList.toImmutableList(),
                        searchResultListUiState = if (resultList.isEmpty()) {
                            SearchUiState.Empty
                        } else {
                            SearchUiState.Success
                        },
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

        if (isNewSearch && executingSearchKeyword == keyword) executingSearchKeyword = null
    }

    private fun postSearchExecution(keyword: String) = viewModelScope.launch {
        searchRepository.postSearchExecution(keyword)
            .onSuccess {
                Timber.d("검색 실행 집계 성공: $keyword")
            }
            .onFailure { error ->
                Timber.e("검색 실행 집계에 실패했습니다. $error")
            }
    }

    fun onBackClick() = viewModelScope.launch {
        _sideEffect.send(SideEffect.NavigateBack)
    }

    fun onCardItemClick(id: Int) = viewModelScope.launch {
        _sideEffect.send(SideEffect.NavigateToJobDetail(id))
    }

    companion object {
        private const val SEARCH_NETWORK_DEBOUNCE = 200L
        private const val DEFAULT_PAGE_SIZE = 20
    }

}
