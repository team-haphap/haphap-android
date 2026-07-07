package com.haphap.app.presentation.search

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(SearchContract.State())
    val uiState = _uiState.asStateFlow()

    val searchInputState = TextFieldState()

    init {
        observeSearchInput()
    }

    @OptIn(FlowPreview::class)
    private fun observeSearchInput() = viewModelScope.launch {
        snapshotFlow { searchInputState.text }
            .debounce(SEARCH_NETWORK_DEBOUNCE)
            .distinctUntilChanged()
            .collectLatest { searchInputText ->
                if (searchInputText.isEmpty()) {
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

    fun updateSelectedChips(id: Int){
        _uiState.update {
            it.toggleCategoryChips(id)
        }
    }

    companion object {
        private const val SEARCH_NETWORK_DEBOUNCE = 500L
    }

}
