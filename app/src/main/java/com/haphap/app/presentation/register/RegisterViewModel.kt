package com.haphap.app.presentation.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegistrationCheckModel
import com.haphap.app.data.repository.api.register.RegisterRepository
import com.haphap.app.presentation.register.navigation.Register
import com.haphap.app.presentation.register.type.NotificationChannelType
import com.haphap.app.presentation.register.type.PassResultStatusButton
import com.haphap.app.presentation.register.type.RegisterResultType
import com.haphap.app.presentation.register.RegisterContract.SideEffect.OnShowToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val postingRepository: RegisterRepository,
    private val registrationRepository: RegisterRepository,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<Register>()
    private val _uiState = MutableStateFlow(RegisterContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<RegisterContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        loadAnnounceList()
    }

    private fun loadAnnounceList() {
        _uiState.update { it.copy(announceListUiState = RegisterUiState.Loading) }

        viewModelScope.launch {
            postingRepository.getPostingNames()
                .onSuccess { list ->
                    _uiState.update {
                        it.copy(
                            announceList = list.toImmutableList(),
                            announceListUiState = RegisterUiState.Success,
                        )
                    }
                }
                .onFailure { e ->
                    Timber.e(e, "loadAnnounceList failed")
                    _uiState.update {
                        it.copy(announceListUiState = RegisterUiState.Failure(e.message ?: "공고 목록을 불러오지 못했습니다."))
                    }
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
            postingRepository.getPostingStages(item.id)
                .onSuccess { list ->
                    _uiState.update {
                        it.copy(
                            processList = list.toImmutableList(),
                            processListUiState = RegisterUiState.Success,
                        )
                    }
                }
                .onFailure { e ->
                    Timber.e(e, "onAnnounceSelected getPostingStages failed")
                    _uiState.update {
                        it.copy(processListUiState = RegisterUiState.Failure(e.message ?: "전형 목록을 불러오지 못했습니다."))
                    }
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
        val result = _uiState.value.selectedResult ?: return
        _uiState.update { it.copy(isChangeModalVisible = false) }
        advanceFromStep2(result)
    }

    fun onChangeModalCancelClick() {
        _uiState.update { it.copy(isChangeModalVisible = false) }
    }

    fun onStep2NextClick() {
        val currentState = _uiState.value
        val selectedResult = currentState.selectedResult ?: return
        val postingId = currentState.registerInfo.postingId ?: return
        val stageId = currentState.registerInfo.stageId ?: return
        val result = selectedResult.toRegisterResultType()

        viewModelScope.launch {
            registrationRepository.checkRegistration(postingId, stageId, result)
                .onSuccess { checkResult ->
                    when (checkResult) {
                        RegistrationCheckModel.NEW -> advanceFromStep2(selectedResult)
                        RegistrationCheckModel.CONFIRM_REQUIRED -> {
                            _uiState.update { it.copy(isChangeModalVisible = true) }
                        }
                        RegistrationCheckModel.DUPLICATE -> {
                            _sideEffect.send(OnShowToast("이미 등록한 결과입니다."))
                            _uiState.update {
                                it.copy(selectedResult = null, isButtonEnabled = false)
                            }
                        }
                    }
                }
                .onFailure { e ->
                    Timber.e(e, "onStep2NextClick checkRegistration failed")
                }
        }
    }

    private fun advanceFromStep2(result: PassResultStatusButton) {
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
        val currentState = _uiState.value
        if (!currentState.isButtonEnabled) return

        val result = currentState.selectedResult?.toRegisterResultType() ?: return
        val registerInfo = currentState.registerInfo.copy(result = result)

        _uiState.update {
            it.copy(registerUiState = RegisterUiState.Loading, isButtonEnabled = false)
        }

        viewModelScope.launch {
            registrationRepository.postRegistration(registerInfo)
                .onSuccess { registrationModel ->
                    _uiState.update {
                        it.copy(
                            registerInfo = registerInfo,
                            registrationResult = registrationModel,
                            registerUiState = RegisterUiState.Success,
                            step = 5,
                            isButtonEnabled = true,
                        )
                    }
                }
                .onFailure { e ->
                    Timber.e(e, "onRegisterClick postRegistration failed")
                    _uiState.update {
                        it.copy(
                            registerUiState = RegisterUiState.Failure(e.message ?: "상태 등록에 실패했습니다."),
                            isButtonEnabled = true,
                        )
                    }
                }
        }
    }
}

private fun PassResultStatusButton.toRegisterResultType(): RegisterResultType = when (this) {
    PassResultStatusButton.PASS -> RegisterResultType.PASS
    PassResultStatusButton.FAILED -> RegisterResultType.FAIL
    PassResultStatusButton.DONT_KNOW -> RegisterResultType.PENDING
}