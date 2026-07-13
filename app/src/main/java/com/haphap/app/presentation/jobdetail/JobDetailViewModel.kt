package com.haphap.app.presentation.jobdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.haphap.app.data.repository.api.detail.JobDetailRepository
import com.haphap.app.presentation.jobdetail.navigation.JobDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class JobDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val jobDetailRepository: JobDetailRepository,
) : ViewModel() {

    private val postingId: Int = savedStateHandle.toRoute<JobDetail>().postingId

    private val _uiState = MutableStateFlow(JobDetailContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        fetchJobPostingDetail()
        fetchJobPostingStages()
        fetchJobPostingStageStatuses()
    }

    fun updateSelectedTab(index: Int) {
        _uiState.update { currentState ->
            currentState.copy(selectedTab = index)
        }
    }

    private fun fetchJobPostingDetail() {
        _uiState.update { it.copy(uiState = JobDetailUiState.Loading) }

        viewModelScope.launch {
            jobDetailRepository.getJobPostingDetail(postingId)
                .onSuccess { model ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            titleInfo = model.titleInfo,
                            bannerImageUrl = model.bannerImageUrl,
                            participant = model.participant,
                            reports = model.reports,
                            uiState = JobDetailUiState.Success,
                        )
                    }
                }
                .onFailure { throwable ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            uiState = JobDetailUiState.Failure(
                                msg = throwable.message ?: "공고 상세 조회 중 오류가 발생했습니다.",
                            ),
                        )
                    }
                }
        }
    }

    private fun fetchJobPostingStages() {
        viewModelScope.launch {
            jobDetailRepository.getJobPostingStages(postingId)
                .onSuccess { resultTabs ->
                    _uiState.update { currentState ->
                        currentState.copy(resultTabs = resultTabs)
                    }
                }
                .onFailure { throwable ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            uiState = JobDetailUiState.Failure(
                                msg = throwable.message ?: "전형 단계 조회 중 오류가 발생했습니다.",
                            ),
                        )
                    }
                }
        }
    }

    private fun fetchJobPostingStageStatuses() {
        viewModelScope.launch {
            jobDetailRepository.getJobPostingStageStatuses(postingId)
                .onSuccess { stages ->
                    _uiState.update { currentState ->
                        currentState.copy(stages = stages)
                    }
                }
                .onFailure { throwable ->
                    _uiState.update { currentState ->
                        currentState.copy(
                            uiState = JobDetailUiState.Failure(
                                msg = throwable.message ?: "전형 단계 진행 상태 조회 중 오류가 발생했습니다.",
                            ),
                        )
                    }
                }
        }
    }
}
