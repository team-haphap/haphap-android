package com.haphap.app.presentation.joblist

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class JobListViewModel @Inject constructor(

): ViewModel() {

    private val _uiState = MutableStateFlow(JobListContract.State())
    val uiState = _uiState.asStateFlow()

    fun updateSelectedChips(category: String) {
        _uiState.update {
            it.copy(categoryChipState = it.categoryChipState.toggle(category))
        }
    }

}
