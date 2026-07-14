package com.haphap.app.presentation.jobdetail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.haphap.app.data.repository.api.detail.JobDetailRepository
import com.haphap.app.presentation.jobdetail.JobDetailContract.SideEffect.OnShowToast
import com.haphap.app.presentation.jobdetail.navigation.JobDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.collections.immutable.toImmutableList
import timber.log.Timber

@HiltViewModel
class JobDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val jobDetailRepository: JobDetailRepository,
) : ViewModel() {

    private val postingId: Int = savedStateHandle.toRoute<JobDetail>().postingId

    private val _uiState = MutableStateFlow(JobDetailContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<JobDetailContract.SideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        fetchJobPostingDetail()
        fetchJobPostingStages()
        fetchJobPostingStageStatuses()
        recordView()
    }

    private fun recordView() {
        viewModelScope.launch {
            jobDetailRepository.recordView(postingId)
                .onFailure { throwable ->
                    Timber.e("$throwable 공고 조회 기록 실패했습니다.")
                }
        }
    }

    fun onRefreshClick() {
        viewModelScope.launch {
            jobDetailRepository.getJobPostingDetail(postingId)
                .onSuccess { model ->
                    Timber.d("실시간 전형 제보 새로고침 성공했습니다.")
                    _uiState.update { currentState ->
                        currentState.copy(reports = model.reports)
                    }
                }
                .onFailure { throwable ->
                    Timber.e("$throwable 실시간 전형 제보 조회 실패했습니다.")
                }
        }
    }

    fun updateSelectedTab(index: Int) {
        _uiState.update { currentState ->
            currentState.copy(selectedTab = index)
        }
        _uiState.value.resultTabs.getOrNull(index)?.let { tab ->
            fetchJobPostingStageStatistic(tab.stageId)
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
                    Timber.e("$throwable 공고 상세 조회 실패했습니다.")
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
                        currentState.copy(resultTabs = resultTabs.toImmutableList())
                    }
                    resultTabs.getOrNull(_uiState.value.selectedTab)?.let { tab ->
                        fetchJobPostingStageStatistic(tab.stageId)
                    }
                }
                .onFailure { throwable ->
                    Timber.e("$throwable 전형 단계 조회 실패했습니다.")
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

    private fun fetchJobPostingStageStatistic(stageId: Int) {
        viewModelScope.launch {
            jobDetailRepository.getJobPostingStageStatistic(postingId, stageId)
                .onSuccess { result ->
                    _uiState.update { currentState ->
                        currentState.copy(result = result)
                    }
                }
                .onFailure { throwable ->
                    Timber.e("$throwable 전형별 집계 조회 실패했습니다.")
                    _uiState.update { currentState ->
                        currentState.copy(
                            uiState = JobDetailUiState.Failure(
                                msg = throwable.message ?: "전형별 집계 조회 중 오류가 발생했습니다.",
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
                        currentState.copy(stages = stages.toImmutableList())
                    }
                }
                .onFailure { throwable ->
                    Timber.e("$throwable 전형 단계 진행 상태 조회 실패했습니다.")
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

    fun onAlarmClick() {
        viewModelScope.launch {
            val isAlarmActive = _uiState.value.isAlarmActive

            if (isAlarmActive) {
                jobDetailRepository.deleteJobPostingAlarm(postingId)
                    .onSuccess {
                        _uiState.update { currentState ->
                            currentState.copy(isAlarmActive = false)
                        }
                        _sideEffect.send(OnShowToast(message = "알림 설정이 해제되었어요!"))
                    }
                    .onFailure { throwable ->
                        Timber.e("$throwable 알람 해제 실패했습니다.")
                    }
            } else {
                jobDetailRepository.setJobPostingAlarm(postingId)
                    .onSuccess {
                        _uiState.update { currentState ->
                            currentState.copy(isAlarmActive = true)
                        }
                        _sideEffect.send(OnShowToast(message = "알림 설정이 완료되었어요!"))
                    }
                    .onFailure { throwable ->
                        Timber.e("$throwable 알람 설정 실패했습니다.")
                    }
            }
        }
    }
}