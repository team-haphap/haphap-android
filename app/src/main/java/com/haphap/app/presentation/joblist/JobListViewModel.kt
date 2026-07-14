package com.haphap.app.presentation.joblist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.JobListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class JobListViewModel @Inject constructor(
    private val jobListRepository: JobListRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(JobListContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        getJobList()
    }

    fun updateSelectedChips(category: String) {
        _uiState.update {
            it.copy(categoryChipState = it.categoryChipState.toggle(category))
        }
        getJobList()
    }

    fun getJobList() = viewModelScope.launch {
        _uiState.update { it.copy(jobListUiState = JobListUiState.Loading) }
        val category = _uiState.value.categoryChipState.selectedChips
        jobListRepository.getJobList(category = category)
            .onSuccess { result ->
                _uiState.update {
                    it.copy(
                        jobList = result.toImmutableList(),
                        jobListUiState = if (result.isEmpty()) {
                            JobListUiState.Empty
                        } else {
                            JobListUiState.Success
                        }
                    )
                }
            }
            .onFailure { error ->
                Timber.e("$error 공고 리스트 조회에 실패했습니다.")
                _uiState.update { it.copy(jobListUiState = JobListUiState.Failure("$error")) }
            }
    }

}
