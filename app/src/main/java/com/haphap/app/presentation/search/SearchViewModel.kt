package com.haphap.app.presentation.search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
                .onSuccess {
                    Timber.d("저장 성공했습니다.")
                }
                .onFailure {
                    Timber.e("$it 저장 실패했습니다.")

                }
            _uiState.update { it.copy(searchAutoCompleteUiState = SearchUiState.Idle) }
            //Todo: 검색 결과 api 호출
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


    companion object {
        private const val SEARCH_NETWORK_DEBOUNCE = 500L
    }

}
