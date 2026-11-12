package com.haphap.app.presentation.passcertification

import androidx.compose.runtime.Immutable
import com.haphap.app.data.model.passcertification.PassCertificationModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed interface PassCertificationContract {
    @Immutable
    data class State(
        val certificationList: ImmutableList<PassCertificationModel> = persistentListOf(),
        val passCertificationUiState: PassCertificationUiState = PassCertificationUiState.Idle,
    )
}

sealed interface PassCertificationUiState {
    data object Idle : PassCertificationUiState
    data object Loading : PassCertificationUiState
    data object Success : PassCertificationUiState
    data class Failure(
        val msg: String,
    ) : PassCertificationUiState
}
