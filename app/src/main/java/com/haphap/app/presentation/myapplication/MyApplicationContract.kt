package com.haphap.app.presentation.myapplication

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.myapplication.MyApplicationModel
import com.haphap.app.presentation.myapplication.type.MyApplicationFilterType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface MyApplicationContract {
    @Immutable
    data class State(
        val selectedFilter: MyApplicationFilterType = MyApplicationFilterType.ALL,
        val inProgressCount: Int = 0,
        val closedCount: Int = 0,
        val applicationList: ImmutableList<MyApplicationModel> = persistentListOf(),
    ) {
        fun filterLabel(filter: MyApplicationFilterType): String =
            when (filter) {
                MyApplicationFilterType.ALL -> filter.label
                MyApplicationFilterType.IN_PROGRESS -> "${filter.label} $inProgressCount"
                MyApplicationFilterType.CLOSED -> "${filter.label} $closedCount"
            }
    }
}
