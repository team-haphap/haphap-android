package com.haphap.app.presentation.search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.search.SearchRepository
import com.haphap.app.presentation.search.SearchContract.SideEffect.OnShowToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
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
                    // Todo: api 호출
                }
            }
    }

    fun updateSelectedChips(category: String) {
        _uiState.update {
            it.copy(categoryChipState = it.categoryChipState.toggle(category))
        }
    }

    fun onSearchClick() = viewModelScope.launch {
        if (searchInputState.text.isBlank()) {
            _sideEffect.send(OnShowToast("검색어를 입력해주세요"))
        } else {
            searchRepository.saveRecentSearchItem(searchInputState.text.toString())
            _uiState.update { it.copy(searchAutoCompleteUiState = SearchUiState.Idle) }
            //Todo: 검색 결과 api 호출
        }
    }

    fun getRecentSearchList() = viewModelScope.launch {
        searchRepository.getRecentSearchItem()
            .onSuccess { flow ->
                flow.collect { list ->
                    _uiState.update { it.copy(recentSearchList = list.toImmutableList()) }
                }
            }
            .onFailure {
                Timber.e(it)
            }
    }

    fun deleteRecentSearchItem(id: Long) = viewModelScope.launch {
        searchRepository.deleteRecentSearchItem(id)
    }


    companion object {
        private const val SEARCH_NETWORK_DEBOUNCE = 500L
    }

}
