package com.haphap.app.presentation.myapplication

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.myapplication.MyApplicationFilter
import com.haphap.app.data.model.myapplication.MyApplicationModel
import com.haphap.app.presentation.myapplication.type.MyApplicationFilterType
import com.haphap.app.presentation.myapplication.type.toFilter
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

sealed interface MyApplicationContract {
    @Immutable
    data class State(
        val selectedFilters: PersistentList<MyApplicationFilterType> =
            persistentListOf(MyApplicationFilterType.ALL),
        val inProgressCount: Int = 0,
        val closedCount: Int = 0,
        val applicationList: ImmutableList<MyApplicationModel> = persistentListOf(),
    ) {
        val queryFilter: MyApplicationFilter
            get() = selectedFilters.singleOrNull()?.toFilter() ?: MyApplicationFilter.ALL

        fun toggle(filter: MyApplicationFilterType): State =
            copy(
                selectedFilters = when {
                    filter == MyApplicationFilterType.ALL -> persistentListOf(MyApplicationFilterType.ALL)
                    selectedFilters.size == 1 && filter in selectedFilters -> selectedFilters
                    filter in selectedFilters -> selectedFilters.remove(filter)
                    else -> selectedFilters.remove(MyApplicationFilterType.ALL).add(filter)
                }
            )

        fun filterLabel(filter: MyApplicationFilterType): String =
            when (filter) {
                MyApplicationFilterType.ALL -> filter.label
                MyApplicationFilterType.IN_PROGRESS -> "${filter.label} $inProgressCount"
                MyApplicationFilterType.CLOSED -> "${filter.label} $closedCount"
            }
    }
}
