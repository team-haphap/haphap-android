package com.haphap.app.presentation.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.presentation.register.type.PassResultStatusButton
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime

@HiltViewModel
class RegisterViewModel @Inject constructor(
    // TODO: Repository 주입
) : ViewModel() {
    private val _uiState = MutableStateFlow(RegisterContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        loadAnnounceList()
    }

    private fun loadAnnounceList() {
    }

    fun onAnnounceSelected(item: RegisterDropDownItemModel) {
        _uiState.update {
            it.copy(
                selectedAnnounce = item,
                selectedProcessId = null,
                processList = persistentListOf(),
                processListUiState = RegisterUiState.Loading,
            )
        }
    }

    fun onProcessSelected(id: Int) {
        _uiState.update { it.copy(selectedProcessId = id) }
    }

    fun onStep1NextClick() {
        if (!_uiState.value.isStep1NextEnabled) return
        _uiState.update { it.copy(step = RegisterStep.RESULT) }
    }

    fun onResultSelected(result: PassResultStatusButton) {
        val previous = _uiState.value.previousRegisteredResult

        when {
            previous == result -> {
                // TODO: 토스트 표시
            }

            previous != null && previous != result -> {
                _uiState.update {
                    it.copy(selectedResult = result, isChangeModalVisible = true)
                }
            }

            else -> {
                _uiState.update { it.copy(selectedResult = result) }
            }
        }
    }

    fun onChangeModalConfirmClick() {
        _uiState.update { it.copy(isChangeModalVisible = false) }
    }

    fun onChangeModalCancelClick() {
        _uiState.update {
            it.copy(
                isChangeModalVisible = false,
                step = RegisterStep.ANNOUNCE_AND_PROCESS,
                selectedResult = null,
            )
        }
    }

    fun onStep2NextClick() {
        val result = _uiState.value.selectedResult ?: return
        _uiState.update {
            it.copy(
                step = if (result == PassResultStatusButton.DONT_KNOW) {
                    RegisterStep.CONFIRM
                } else {
                    RegisterStep.DATE_AND_CHANNEL
                },
            )
        }
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update { it.copy(contactDate = date) }
    }

    fun onTimeSelected(time: LocalTime) {
        _uiState.update { it.copy(contactTime = time) }
    }

    fun onChannelToggled(channel: NotificationChannelType) {
        _uiState.update { it.toggleNotificationChannel(channel) }
    }

    fun onStep3NextClick() {
        if (!_uiState.value.isStep3NextEnabled) return
        _uiState.update { it.copy(step = RegisterStep.CONFIRM) }
    }

    fun onAlarmAgreeToggled(checked: Boolean) {
        _uiState.update { it.copy(isAlarmAgreed = checked) }
    }

    fun onTermAgreeToggled(checked: Boolean) {
        _uiState.update { it.copy(isTermsAgreed = checked) }
    }

    fun onRegisterClick() {
        if (!_uiState.value.isRegisterButtonEnabled) return

        viewModelScope.launch {
            _uiState.update { it.copy(registerUiState = RegisterUiState.Loading) }
        }
    }
}