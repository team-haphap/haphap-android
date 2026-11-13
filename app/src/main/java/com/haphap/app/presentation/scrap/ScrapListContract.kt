package com.haphap.app.presentation.scrap

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.scrap.ScrapModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface ScrapListContract {
    @Immutable
    data class State(
        val scrapList: ImmutableList<ScrapModel> = persistentListOf(),
        val scrapListUiState: ScrapListUiState = ScrapListUiState.Idle,
    ) {
        val isEmpty: Boolean
            get() = scrapListUiState is ScrapListUiState.Success && scrapList.isEmpty()
    }

    sealed interface SideEffect {
        data class OnShowToast(
            val message: String,
            val isAlarm: Boolean = false,
        ) : SideEffect
    }
}

sealed interface ScrapListUiState {
    data object Idle : ScrapListUiState
    data object Loading : ScrapListUiState
    data object Success : ScrapListUiState
    data class Failure(
        val msg: String,
    ) : ScrapListUiState
}
