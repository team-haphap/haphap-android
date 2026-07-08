package com.haphap.app.presentation.joblist

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.list.JobItemModel
import com.haphap.app.presentation.category.CategoryChipState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface JobListContract {

    @Immutable
    data class State(
        val categoryChipState: CategoryChipState = CategoryChipState(),
        val jobList: ImmutableList<JobItemModel> = persistentListOf(),
    )
}
