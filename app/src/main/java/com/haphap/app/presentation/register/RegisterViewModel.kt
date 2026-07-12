package com.haphap.app.presentation.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.presentation.register.navigation.Register
import com.haphap.app.presentation.register.type.PassResultStatusButton
import com.haphap.app.presentation.register.type.RegisterContactedMethodType
import com.haphap.app.presentation.register.type.RegisterResultType
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
    private val entryPoint : RegisterSideEffect = route.jobId?.let { jobId ->
        RegisterSideEffect.JobDetail(jobId)
    } ?: RegisterSideEffect.Home
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
                    announceList = DUMMY_ANNOUNCE_LIST,
                    announceListUiState = RegisterUiState.Success,
                )
            }
        }
    }

    fun onAnnounceSelected(item: RegisterDropDownItemModel) {
        _uiState.update {
            it.copy(
                selectedAnnounce = item,
                selectedProcessId = null,
                processList = persistentListOf(),
                processListUiState = RegisterUiState.Loading,
                previousRegisteredResult = null,
                isButtonEnabled = false,
            )
        }

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    processList = DUMMY_PROCESS_LIST_BY_ANNOUNCE_ID[item.id] ?: persistentListOf(),
                    processListUiState = RegisterUiState.Success,
                )
            }
        }
    }

    fun onProcessSelected(id: Int) {
        _uiState.update {
            it.copy(
                selectedProcessId = id,
                previousRegisteredResult = null,
                isButtonEnabled = it.selectedAnnounce != null,
            )
        }
    }

    fun onStep1NextClick() {
        if (!_uiState.value.isButtonEnabled) return
        _uiState.update { it.copy(step = 2, isButtonEnabled = it.selectedResult != null) }
    }

    fun onResultSelected(result: PassResultStatusButton) {
        val previous = _uiState.value.previousRegisteredResult

        when {
            previous == result -> {
                // TODO: 동일 공고-전형-결과 재등록 -> 중복 등록 토스트 트리거
            }

            previous != null && previous != result -> {
                _uiState.update {
                    it.copy(
                        selectedResult = result,
                        isChangeModalVisible = true,
                        isButtonEnabled = true,
                    )
                }
            }

            else -> {
                _uiState.update {
                    it.copy(selectedResult = result, isButtonEnabled = true)
                }
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
                isButtonEnabled = it.selectedAnnounce != null && it.selectedProcessId != null
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
                    it.contactDate != null && it.contactTime != null
                } else {
                    it.isTermsAgreed
                },
            )
        }
    }

    fun onDateSelected(date: LocalDate) {
        _uiState.update {
            it.copy(
                contactDate = date,
                isButtonEnabled = it.contactTime != null
            )
        }
    }

    fun onTimeSelected(time: LocalTime) {
        _uiState.update {
            it.copy(
                contactTime = time,
                isButtonEnabled = it.contactDate != null,
            )
        }
    }

    fun onChannelToggled(channel: NotificationChannelType) {
        _uiState.update { it.toggleNotificationChannel(channel) }
    }

    fun onStep3NextClick() {
        if (!_uiState.value.isButtonEnabled) return
        _uiState.update { it.copy(step = 4, isButtonEnabled = it.isTermsAgreed) }
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
                    1 -> it.selectedAnnounce != null && it.selectedProcessId != null
                    2 -> it.selectedResult != null
                    3 -> it.contactDate != null && it.contactTime != null
                    else -> it.isTermsAgreed
                },
            )
        }
    }

    fun onAlarmAgreeToggled(checked: Boolean) {
        _uiState.update { it.copy(isAlarmAgreed = checked) }
    }

    fun onTermAgreeToggled(checked: Boolean) {
        _uiState.update {
            it.copy(
                isTermsAgreed = checked,
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

            val registerModel = _uiState.value.toRegisterModel()
            // TODO: registerModel을 Repository에 전달하여 실제 등록 API 호출

            _uiState.update {
                it.copy(
                    registerUiState = RegisterUiState.Success,
                    step = 5,
                    passShareInfo = null,
                    isButtonEnabled = true,
                )
            }
        }
    }

    private fun RegisterContract.State.toRegisterModel(): RegisterModel =
        RegisterModel(
            postingId = selectedAnnounce?.id ?: 0,
            stageId = selectedProcessId ?: 0,
            result = selectedResult.toRegisterResultType(),
            contactedDate = contactDate?.toString().orEmpty(),
            contactedTime = contactTime?.toString().orEmpty(),
            contactedMethod = selectedChannels.firstOrNull()?.toRegisterContactedMethodType()
                ?: RegisterContactedMethodType.ETC,
            anonymous = isTermsAgreed,
            alarmEnabled = isAlarmAgreed,
        )

    private fun PassResultStatusButton?.toRegisterResultType(): RegisterResultType =
        when (this) {
            PassResultStatusButton.PASS -> RegisterResultType.PASS
            PassResultStatusButton.FAILED -> RegisterResultType.FAIL
            PassResultStatusButton.DONT_KNOW, null -> RegisterResultType.PENDING
        }

    private fun NotificationChannelType.toRegisterContactedMethodType(): RegisterContactedMethodType =
        when (this) {
            NotificationChannelType.SMS -> RegisterContactedMethodType.SMS
            NotificationChannelType.EMAIL -> RegisterContactedMethodType.EMAIL
            NotificationChannelType.CALL -> RegisterContactedMethodType.PHONE_CALL
            NotificationChannelType.WEB -> RegisterContactedMethodType.MY_PAGE
        }

    companion object {
        private val DUMMY_ANNOUNCE_LIST = persistentListOf(
            RegisterDropDownItemModel(id = 1, text = "카카오 2026 신입 개발자 공개 채용"),
            RegisterDropDownItemModel(id = 2, text = "네이버 2026 신입 개발자 공개 채용"),
            RegisterDropDownItemModel(id = 3, text = "라인 2026 신입 기획자 공개 채용"),
        )

        private val DUMMY_PROCESS_LIST_BY_ANNOUNCE_ID = mapOf(
            1 to persistentListOf(
                RegisterProcessModel(id = 1, text = "서류"),
                RegisterProcessModel(id = 2, text = "AI 역량 검사"),
                RegisterProcessModel(id = 3, text = "1차면접"),
                RegisterProcessModel(id = 4, text = "2차면접"),
                RegisterProcessModel(id = 5, text = "임원면접"),
                RegisterProcessModel(id = 6, text = "최종"),
            ),
            2 to persistentListOf(
                RegisterProcessModel(id = 7, text = "서류"),
                RegisterProcessModel(id = 8, text = "코딩테스트"),
                RegisterProcessModel(id = 9, text = "면접"),
            ),
            3 to persistentListOf(
                RegisterProcessModel(id = 10, text = "서류"),
                RegisterProcessModel(id = 11, text = "과제전형"),
                RegisterProcessModel(id = 12, text = "면접"),
                RegisterProcessModel(id = 13, text = "최종"),
            ),
        )
    }
}