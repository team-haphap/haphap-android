package com.haphap.app.presentation.passcard.storage

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.passcard.PassCardStorageModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface PassCardStorageContract {
    @Immutable
    data class State(
        val passCardStorageList: ImmutableList<PassCardStorageModel> = persistentListOf(),
        val isLoaded: Boolean = false,
    )

    sealed interface SideEffect {
        data object NavigateBack : SideEffect
        data object NavigateToRegister : SideEffect
        data class NavigateToPassCardDetail(val id: Int) : SideEffect
    }
}