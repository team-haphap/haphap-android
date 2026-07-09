package com.haphap.app.presentation.jobdetail

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.detail.JobParticipantModel
import com.haphap.app.data.model.detail.JobResultModel
import com.haphap.app.data.model.detail.JobStepModel
import com.haphap.app.data.model.detail.JobStepReportModel
import com.haphap.app.data.model.detail.JobTitleModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface JobDetailContract {
    @Immutable
    data class State(
        val titleInfo: JobTitleModel = JobTitleModel(),
        val bannerImageUrl: String = "",
        val isAlarmActive: Boolean = false,
        val stages: ImmutableList<JobStepModel> = persistentListOf(),
        val resultTabs: ImmutableList<String> = persistentListOf(),
        val selectedTab: String = "",
        val result: JobResultModel = JobResultModel(),
        val participant: JobParticipantModel = JobParticipantModel(),
        val reports: ImmutableList<JobStepReportModel> = persistentListOf(),
        val uiState: JobDetailUiState = JobDetailUiState.Idle,
    )
}

sealed interface JobDetailUiState {
    data object Idle : JobDetailUiState
    data object Loading : JobDetailUiState
    data object Success : JobDetailUiState
    data class Failure(
        val msg: String,
    ) : JobDetailUiState
}