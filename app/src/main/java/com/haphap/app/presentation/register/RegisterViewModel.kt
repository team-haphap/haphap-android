package com.haphap.app.presentation.register

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterPassShareModel
import com.haphap.app.data.model.register.RegisterProcessModel
import com.haphap.app.presentation.register.navigation.Register
import com.haphap.app.presentation.register.type.PassResultStatusButton
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.delay
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
        // 수정: 전체 플로우 테스트를 위한 임시 하드코딩 (API 연동 전까지만 사용)
        _uiState.update { it.copy(announceListUiState = RegisterUiState.Loading) }

        viewModelScope.launch {
            delay(300)
            _uiState.update {
                it.copy(
                    announceList = DUMMY_ANNOUNCE_LIST,
                    announceListUiState = RegisterUiState.Success,
                )
            }
        }
    }

    // ---------- Step 1 (REG-002) ----------

    fun onAnnounceSelected(item: RegisterDropDownItemModel) {
        _uiState.update {
            it.copy(
                selectedAnnounce = item,
                selectedProcessId = null,
                processList = persistentListOf(),
                processListUiState = RegisterUiState.Loading,
                previousRegisteredResult = null,
            )
        }

        viewModelScope.launch {
            delay(300)
            _uiState.update {
                it.copy(
                    processList = DUMMY_PROCESS_LIST_BY_ANNOUNCE_ID[item.id] ?: persistentListOf(),
                    processListUiState = RegisterUiState.Success,
                )
            }
        }
    }

    fun onProcessSelected(id: Int) {
        val announceId = _uiState.value.selectedAnnounce?.id
        val previousResult = DUMMY_PREVIOUS_RESULTS["$announceId-$id"]
        _uiState.update {
            it.copy(
                selectedProcessId = id,
                previousRegisteredResult = previousResult,
            )
        }
    }

    fun onStep1NextClick() {
        if (!_uiState.value.isStep1NextEnabled) return
        _uiState.update { it.copy(step = RegisterStep.RESULT) }
    }

    // ---------- Step 2 (REG-003, REG-004) ----------

    fun onResultSelected(result: PassResultStatusButton) {
        val previous = _uiState.value.previousRegisteredResult

        when {
            previous == result -> {
                // TODO: 동일 공고-전형-결과 재등록 -> 중복 등록 토스트 트리거 (팀원 구현 컴포넌트 연동 예정)
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

    // ---------- Step 3 (REG-005) ----------

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

    // ---------- Confirm (REG-006) ----------

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
            delay(300)

            // 수정: 전체 플로우 테스트를 위한 임시 하드코딩 (API 연동 전까지만 사용)
            val announceId = _uiState.value.selectedAnnounce?.id
            _uiState.update {
                it.copy(
                    registerUiState = RegisterUiState.Success,
                    step = RegisterStep.COMPLETE,
                    passShareInfo = DUMMY_PASS_SHARE_INFO_BY_ANNOUNCE_ID[announceId],
                )
            }
        }
    }

    // ---------- PASS_SHARE (REG-007 연장, PASS 전용) ----------

    fun onPassShareEntryClick() {
        _uiState.update { it.copy(step = RegisterStep.PASS_SHARE) }
    }

    companion object {
        // 수정: 전체 플로우 테스트용 더미 데이터. API 연동 시 전부 제거하고 Repository 호출로 대체 예정.

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

        // key: "공고id-전형id" -> 이전에 등록된 결과 (변경 모달 테스트용)
        private val DUMMY_PREVIOUS_RESULTS = mapOf(
            "1-1" to PassResultStatusButton.FAILED,
        )

        // key: 공고id -> 합격 축하 카드 정보 (PASS_SHARE 화면 테스트용)
        private val DUMMY_PASS_SHARE_INFO_BY_ANNOUNCE_ID = mapOf(
            1 to RegisterPassShareModel(
                companyName = "카카오",
                logoUrl = "",
                backgroundImageUrl = "",
            ),
            2 to RegisterPassShareModel(
                companyName = "네이버",
                logoUrl = "",
                backgroundImageUrl = "",
            ),
            3 to RegisterPassShareModel(
                companyName = "라인",
                logoUrl = "",
                backgroundImageUrl = "",
            ),
        )
    }
}