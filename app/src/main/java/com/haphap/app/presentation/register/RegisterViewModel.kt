package com.haphap.app.presentation.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.presentation.register.navigation.Register
import com.haphap.app.presentation.register.type.NotificationChannelType
import com.haphap.app.presentation.register.type.PassResultStatusButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    // TODO: 공고/전형/등록 Repository 주입 (API 연동 시 하단 더미 데이터를 전부 대체)
) : ViewModel() {

    private val route = savedStateHandle.toRoute<Register>()
    private val _uiState = MutableStateFlow(RegisterContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        loadAnnounceList()
    }

    private fun loadAnnounceList() {
        _uiState.update { it.copy(announceListUiState = RegisterUiState.Loading) }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    // TODO: 추후 연동
                    // announceList = ,
                    announceListUiState = RegisterUiState.Success,
                )
            }
        }
    }

    fun onAnnounceSelected(item: RegisterDropDownItemModel) {
        _uiState.update {
            it.copy(
                selectedAnnounce = item,
                registerInfo = it.registerInfo.copy(postingId = item.id, stageId = null),
                processList = persistentListOf(),
                processListUiState = RegisterUiState.Loading,
                isButtonEnabled = false,
            )
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    // TODO: API 연동
//                    processList = DUMMY_PROCESS_LIST_BY_ANNOUNCE_ID[item.id] ?: persistentListOf(),
                    processListUiState = RegisterUiState.Success,
                )
            }
        }
    }

    fun onProcessSelected(id: Int) {
        _uiState.update {
            it.copy(
                registerInfo = it.registerInfo.copy(stageId = id),
                isButtonEnabled = it.selectedAnnounce != null,
            )
        }
    }

    fun onStep1NextClick() {
        if (!_uiState.value.isButtonEnabled) return
        _uiState.update { it.copy(step = 2, isButtonEnabled = it.selectedResult != null) }
    }

    fun onResultSelected(result: PassResultStatusButton) {
        _uiState.update {
            it.copy(selectedResult = result, isButtonEnabled = true)
        }

        if (result == PassResultStatusButton.DONT_KNOW) {
                _uiState.update {
                    it.copy(
                        registerInfo = it.registerInfo.copy(contactedDate = null, contactedTime = null),
                    )
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
                step = 1,
                selectedResult = null,
                isButtonEnabled = it.selectedAnnounce != null && it.registerInfo.stageId != null
            )
        }
    }

    fun onStep2NextClick() {
        val result = _uiState.value.selectedResult ?: return
        _uiState.update {
            val nextStep = if (result == PassResultStatusButton.DONT_KNOW) 4 else 3

            it.copy(
                step = nextStep,
                isButtonEnabled = if (nextStep == 3) {
                    it.registerInfo.contactedDate != null && it.registerInfo.contactedTime != null && it.registerInfo.contactedMethod.isNotEmpty()
                } else {
                    it.registerInfo.anonymous
                },
            )
        }
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update {
            it.copy(
                registerInfo = it.registerInfo.copy(contactedDate = date.toString()),
                isButtonEnabled = it.registerInfo.contactedTime != null && it.registerInfo.contactedMethod.isNotEmpty(),
            )
        }
    }

    fun onTimeSelected(time: LocalTime) {
        _uiState.update {
            it.copy(
                registerInfo = it.registerInfo.copy(contactedTime = time.toString()),
                isButtonEnabled = it.registerInfo.contactedDate != null && it.registerInfo.contactedMethod.isNotEmpty(),
            )
        }
    }

    fun onChannelToggled(channel: NotificationChannelType) {
        _uiState.update {
            val toggled = it.toggleNotificationChannel(channel)
            toggled.copy(
                isButtonEnabled = toggled.registerInfo.contactedDate != null &&
                    toggled.registerInfo.contactedTime != null &&
                    toggled.registerInfo.contactedMethod.isNotEmpty(),
            )
        }
    }

    fun onStep3NextClick() {
        if (!_uiState.value.isButtonEnabled) return
        _uiState.update { it.copy(step = 4, isButtonEnabled = it.registerInfo.anonymous) }
    }

    fun onBackClick() {
        _uiState.update {
            val previousStep = if (it.step == 4 && it.selectedResult == PassResultStatusButton.DONT_KNOW) {
                2
            } else {
                it.step - 1
            }
            it.copy(
                step = previousStep,
                isButtonEnabled = when (previousStep) {
                    1 -> it.selectedAnnounce != null && it.registerInfo.stageId != null
                    2 -> it.selectedResult != null
                    3 -> it.registerInfo.contactedDate != null && it.registerInfo.contactedTime != null && it.registerInfo.contactedMethod.isNotEmpty()
                    else -> it.registerInfo.anonymous
                },
            )
        }
    }

    fun onAlarmAgreeToggled(checked: Boolean) {
        _uiState.update { it.copy(registerInfo = it.registerInfo.copy(alarmEnabled = checked)) }
    }

    fun onTermAgreeToggled(checked: Boolean) {
        _uiState.update {
            it.copy(
                registerInfo = it.registerInfo.copy(anonymous = checked),
                isButtonEnabled = checked && it.registerUiState !is RegisterUiState.Loading
            )
        }
    }

    fun onRegisterClick() {
        if (!_uiState.value.isButtonEnabled) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    registerUiState = RegisterUiState.Loading,
                    isButtonEnabled = false
                )
            }

            _uiState.update {
                it.copy(
                    registerUiState = RegisterUiState.Success,
                    step = 5,
                    isButtonEnabled = true,
                )
            }
        }
    }
}