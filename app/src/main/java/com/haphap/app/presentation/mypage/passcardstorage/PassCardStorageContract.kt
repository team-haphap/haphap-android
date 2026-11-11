package com.haphap.app.presentation.mypage.passcardstorage

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.mypage.PassCardStorageModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface PassCardStorageContract {
    @Immutable
    data class State(
        val passCardStorageList: ImmutableList<PassCardStorageModel> = persistentListOf(),
    )

    sealed interface SideEffect {
        data object NavigateBack : SideEffect
        data object NavigateToRegister : SideEffect
        data class NavigateToPassCardDetail(val id: Int) : SideEffect
    }
}