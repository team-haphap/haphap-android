package com.haphap.app.presentation.myapplication

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MyApplicationViewModel @Inject constructor(
    
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyApplicationContract.State())
    val uiState = _uiState.asStateFlow()

    fun onFilterClick(filter: MyApplicationFilterType) {
        _uiState.update { it.copy(selectedFilter = filter) }
    }
}
