package com.haphap.app.presentation.jobdetail

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class JobDetailViewModel @Inject constructor(

) : ViewModel() {

    private val _uiState = MutableStateFlow(JobDetailContract.State())
    val uiState = _uiState.asStateFlow()

    fun updateSelectedTab(index: Int) {
        _uiState.update { currentState ->
            currentState.copy(selectedTab = index) }
    }

}