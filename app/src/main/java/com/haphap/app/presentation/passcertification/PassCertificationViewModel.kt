package com.haphap.app.presentation.passcertification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.passcertification.PassCertificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PassCertificationViewModel @Inject constructor(
    private val passCertificationRepository: PassCertificationRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(PassCertificationContract.State())
    val uiState: StateFlow<PassCertificationContract.State> = _uiState.asStateFlow()

    fun getRejectedCertifications() {
        if (_uiState.value.passCertificationUiState is PassCertificationUiState.Loading) return
        _uiState.update { it.copy(passCertificationUiState = PassCertificationUiState.Loading) }

        viewModelScope.launch {
            passCertificationRepository.getRejectedCertifications()
                .onSuccess { certifications ->
                    _uiState.update {
                        it.copy(
                            certificationList = certifications.toImmutableList(),
                            passCertificationUiState = PassCertificationUiState.Success,
                        )
                    }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            passCertificationUiState = PassCertificationUiState.Failure(
                                msg = throwable.message.orEmpty(),
                            ),
                        )
                    }
                }
        }
    }
}
