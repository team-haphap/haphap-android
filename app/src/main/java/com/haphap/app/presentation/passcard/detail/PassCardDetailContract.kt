package com.haphap.app.presentation.passcard.detail

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.passcard.PassCardDetailChipListModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface PassCardDetailContract {
    @Immutable
    data class State(
        val userName: String = "",
        val recruitName: String = "",
        val companyName: String = "",
        val logoUrl: String = "",
        val backgroundImageUrl: String = "",
        val stageName: String = "",
        val chipList: ImmutableList<PassCardDetailChipListModel> = persistentListOf(),
        val selectedChip: Int = 0,
        val isBackgroundLoaded: Boolean = false,
        val isLogoLoaded: Boolean = false,
    ) {
        val isSaveEnabled: Boolean
            get() = isBackgroundLoaded && isLogoLoaded
    }

    sealed class SideEffect {
        data class OnShowToast (
            val message: String,
            val isAlarm: Boolean = false,
        ): SideEffect()

        data object NavigateToHome : SideEffect()
    }
}
