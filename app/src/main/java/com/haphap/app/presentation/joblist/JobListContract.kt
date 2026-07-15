package com.haphap.app.presentation.joblist

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.list.JobItemModel
import com.haphap.app.presentation.common.state.CategoryChipState
import com.haphap.app.presentation.jobdetail.JobDetailUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface JobListContract {

    @Immutable
    data class State(
        val categoryChipState: CategoryChipState = CategoryChipState(),
        val jobList: ImmutableList<JobItemModel> = persistentListOf(),
        val jobListUiState: JobListUiState = JobListUiState.Idle,
    )
}

sealed interface JobListUiState {
    data object Idle : JobListUiState
    data object Loading : JobListUiState
    data object Empty : JobListUiState
    data object Success : JobListUiState
    data class Failure(
        val msg: String,
    ) : JobListUiState
}
