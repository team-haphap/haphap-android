package com.haphap.app.presentation.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.presentation.register.type.RegisterResultType
import com.haphap.app.data.model.register.RegistrationCheckType
import com.haphap.app.data.repository.api.register.RegisterRepository
import com.haphap.app.presentation.register.navigation.Register
import com.haphap.app.presentation.register.type.NotificationChannelType
import com.haphap.app.presentation.register.type.PassResultStatusButton
import com.haphap.app.presentation.register.RegisterContract.SideEffect.NavigateToHome
import com.haphap.app.presentation.register.RegisterContract.SideEffect.NavigateToJobDetail
import com.haphap.app.presentation.register.RegisterContract.SideEffect.NavigateToPassCard
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
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val registerRepository: RegisterRepository,
) : ViewModel() {

    private val route = savedStateHandle.toRoute<Register>()
    private val _uiState = MutableStateFlow(RegisterContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<RegisterContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        route.jobId?.let { jobId ->
            _uiState.update { it.copy(entryPoint = RegisterContract.RegisterSideEffect.JobDetail(jobId)) }
        }
        loadAnnounceList()
    }

    private fun loadAnnounceList() {
        _uiState.update { it.copy(announceListUiState = RegisterUiState.Loading) }

        viewModelScope.launch {
            registerRepository.getRegisterPostNames()
                .onSuccess { list ->
                    _uiState.update {
                        it.copy(
                            announceList = list.toImmutableList(),
                            announceListUiState = RegisterUiState.Success,
                        )
                    }
                    list.find { it.id == route.jobId }?.let(::onAnnounceSelected)
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
            registerRepository.getRegisterPostStages(item.id)
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
            registerRepository.postCheckRegistration(postingId, stageId, result)
                .onSuccess { checkResult ->
                    when (checkResult) {
                        RegistrationCheckType.NEW -> advanceFromStep2(selectedResult)
                        RegistrationCheckType.CONFIRM_REQUIRED -> {
                            _uiState.update { it.copy(isChangeModalVisible = true) }
                        }
                        RegistrationCheckType.DUPLICATE -> {
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
                    isContactDateTimeValid(it.registerInfo.contactedDate, it.registerInfo.contactedTime) &&
                        it.registerInfo.contactedMethod.isNotEmpty()
                } else {
                    it.registerInfo.anonymous
                },
            )
        }
    }

    fun onDateSelected(date: LocalDate) {
        val updatedInfo = _uiState.value.registerInfo.copy(contactedDate = date.toString())
        val isValid = isContactDateTimeValid(updatedInfo.contactedDate, updatedInfo.contactedTime)

        _uiState.update {
            it.copy(
                registerInfo = updatedInfo,
                isButtonEnabled = isValid && updatedInfo.contactedMethod.isNotEmpty(),
            )
        }

        if (updatedInfo.contactedTime != null && !isValid) {
            viewModelScope.launch {
                _sideEffect.send(
                    OnShowToast(
                        message = "현재 시간 이후로는 선택할 수 없어요",
                        isAlarm = false,
                    )
                )
            }
        }
    }

    fun onTimeSelected(time: LocalTime) {
        val updatedInfo = _uiState.value.registerInfo.copy(contactedTime = time.toString())
        val isValid = isContactDateTimeValid(updatedInfo.contactedDate, updatedInfo.contactedTime)

        _uiState.update {
            it.copy(
                registerInfo = updatedInfo,
                isButtonEnabled = isValid && updatedInfo.contactedMethod.isNotEmpty(),
            )
        }

        if (updatedInfo.contactedDate != null && !isValid) {
            viewModelScope.launch {
                _sideEffect.send(
                    OnShowToast(
                        message = "현재 시간 이후로는 선택할 수 없어요",
                        isAlarm = false,
                    )
                )
            }
        }
    }

    fun onChannelToggled(channel: NotificationChannelType) {
        _uiState.update {
            val toggled = it.toggleNotificationChannel(channel)
            toggled.copy(
                isButtonEnabled = isContactDateTimeValid(toggled.registerInfo.contactedDate, toggled.registerInfo.contactedTime) &&
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
                    3 -> isContactDateTimeValid(it.registerInfo.contactedDate, it.registerInfo.contactedTime) &&
                        it.registerInfo.contactedMethod.isNotEmpty()
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
            registerRepository.postRegister(registerInfo)
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

    fun onFinishClick() {
        val currentState = _uiState.value

        viewModelScope.launch {
            if (currentState.selectedResult == PassResultStatusButton.PASS) {
                currentState.registrationResult?.card?.let { card ->
                    _sideEffect.send(NavigateToPassCard(card))
                }
            } else {
                when (val entryPoint = currentState.entryPoint) {
                    RegisterContract.RegisterSideEffect.Home -> _sideEffect.send(NavigateToHome)
                    is RegisterContract.RegisterSideEffect.JobDetail ->
                        _sideEffect.send(NavigateToJobDetail(entryPoint.jobId))
                }
            }
        }
    }

    private fun isContactDateTimeValid(dateString: String?, timeString: String?): Boolean {
        val date = dateString?.let(LocalDate::parse) ?: return false
        val time = timeString?.let(LocalTime::parse) ?: return false
        return !LocalDateTime.of(date, time).isAfter(LocalDateTime.now())
    }
}

private fun PassResultStatusButton.toRegisterResultType(): RegisterResultType = when (this) {
    PassResultStatusButton.PASS -> RegisterResultType.PASS
    PassResultStatusButton.FAILED -> RegisterResultType.FAIL
    PassResultStatusButton.DONT_KNOW -> RegisterResultType.PENDING
}