package com.haphap.app.presentation.myapplication

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.myapplication.MyApplicationModel
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

enum class MyApplicationFilterType(val label: String) {
    ALL("전체"),
    IN_PROGRESS("전형 진행중"),
    CLOSED("모든 전형 마감"),
}
