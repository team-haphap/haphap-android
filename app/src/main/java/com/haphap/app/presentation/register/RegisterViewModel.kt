package com.haphap.app.presentation.register

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.haphap.app.core.image.ImageFileInfoReader
import com.haphap.app.data.model.register.NotificationChannelType
import com.haphap.app.data.model.register.RegisterDropDownItemModel
import com.haphap.app.data.model.register.RegisterModel
import com.haphap.app.data.model.register.RegistrationCheckType
import com.haphap.app.data.repository.api.register.RegisterRepository
import com.haphap.app.presentation.register.navigation.Register
import com.haphap.app.presentation.register.type.PassResultStatusType
import com.haphap.app.presentation.register.RegisterContract.SideEffect.NavigateToHome
import com.haphap.app.presentation.register.RegisterContract.SideEffect.NavigateBack
import com.haphap.app.presentation.register.RegisterContract.SideEffect.NavigateToJobDetail
import com.haphap.app.presentation.register.RegisterContract.SideEffect.OnShowToast
import com.haphap.app.presentation.register.RegisterContract.SideEffect.OpenMultipleImagePicker
import com.haphap.app.presentation.register.RegisterContract.SideEffect.OpenSingleImagePicker
import com.haphap.app.presentation.register.model.RegisterProofImageModel
import com.haphap.app.presentation.register.type.RegisterProofImageErrorType
import com.haphap.app.presentation.register.type.RegisterStep
import com.haphap.app.presentation.register.type.toResultType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDate
import java.time.LocalTime
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val registerRepository: RegisterRepository,
    private val imageFileInfoReader: ImageFileInfoReader,
) : ViewModel() {
    private var checkRegistrationJob: Job? = null

    private val route = savedStateHandle.toRoute<Register>()
    private val _uiState = MutableStateFlow(RegisterContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<RegisterContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        loadAnnounceList()
    }

    fun onNextClick() {
        val state = _uiState.value
        if (!state.isButtonEnabled) return

        when (state.step) {
            RegisterStep.SELECT_PROCESS,
            RegisterStep.CONTACT,
            RegisterStep.PASS_PROOF -> moveToNextStep(from = state.step)
            RegisterStep.RESULT -> checkRegistration()
            RegisterStep.CONFIRM -> register()
            RegisterStep.COMPLETE -> finish()
        }
    }

    fun onBackClick() {
        val state = _uiState.value
        if (state.registerUiState is RegisterUiState.Loading) return

        val previous = state.step.previous(state.selectedResult)
        if (previous == null) {
            viewModelScope.launch {
                _sideEffect.send(NavigateBack)
            }
        } else {
            _uiState.update { it.copy(step = previous) }
        }
    }

    private fun moveToNextStep(from: RegisterStep) {
        _uiState.update { it.moveToNextStep(from) }
    }

    private fun RegisterContract.State.moveToNextStep(from: RegisterStep)
    : RegisterContract.State {
        if (step != from) return this
        val next = step.next(selectedResult) ?: return this
        return copy(step = next)
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
                        it.copy(
                            announceListUiState = RegisterUiState
                                .Failure(e.message ?: "공고 목록을 불러오지 못했습니다.")
                        )
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
                        it.copy(
                            processListUiState = RegisterUiState
                                .Failure(e.message ?: "전형 목록을 불러오지 못했습니다.")
                        )
                    }
                }
        }
    }

    fun onProcessSelected(id: Int) {
        _uiState.update { it.copy(registerInfo = it.registerInfo.copy(stageId = id)) }
    }

    fun onResultSelected(result: PassResultStatusType) {
        if (_uiState.value.selectedResult != result) {
            checkRegistrationJob?.cancel()
        }
        _uiState.update {
            it.copy(
                selectedResult = result,
                registerInfo = if (result == PassResultStatusType.DONT_KNOW) {
                    it.registerInfo.copy(contactedDate = null, contactedTime = null)
                } else {
                    it.registerInfo
                },
                proofImages = if (result == PassResultStatusType.PASS) {
                    it.proofImages
                } else {
                    persistentListOf()
                }
            )
        }
    }

    private fun checkRegistration() {
        if (checkRegistrationJob?.isActive == true) return

        val state = _uiState.value
        val requestedResult = state.selectedResult ?: return
        val postingId = state.registerInfo.postingId ?: return
        val stageId = state.registerInfo.stageId ?: return

        checkRegistrationJob = viewModelScope.launch {
            registerRepository.postCheckRegistration(
                postingId, stageId, requestedResult.toResultType()
            )
                .onSuccess { checkResult ->
                    val current = _uiState.value
                    if (
                        current.step != RegisterStep.RESULT ||
                        current.selectedResult != requestedResult ||
                        current.registerInfo.postingId != postingId ||
                        current.registerInfo.stageId != stageId
                    ) return@onSuccess

                    when (checkResult) {
                        RegistrationCheckType.NEW ->
                            moveToNextStep(from = RegisterStep.RESULT)

                        RegistrationCheckType.CONFIRM_REQUIRED ->
                            _uiState.update { it.copy(isChangeModalVisible = true) }

                        RegistrationCheckType.DUPLICATE -> {
                            _sideEffect.send(OnShowToast("이미 등록한 결과입니다"))
                            _uiState.update { it.copy(selectedResult = null) }
                        }
                    }
                }
                .onFailure { e ->
                    Timber.e(e, "checkRegistration failed")
                }
        }
    }

    fun onChangeModalConfirmClick() {
        _uiState.update {
            it.copy(isChangeModalVisible = false).moveToNextStep(from = RegisterStep.RESULT)
        }
    }

    fun onChangeModalCancelClick() {
        _uiState.update { it.copy(isChangeModalVisible = false) }
    }

    fun onDateSelected(date: LocalDate) {
        updateContactDateTime { it.copy(contactedDate = date.toString()) }
    }

    fun onTimeSelected(time: LocalTime) {
        updateContactDateTime { it.copy(contactedTime = time.toString()) }
    }

    private fun updateContactDateTime(
        transform: (RegisterModel) -> RegisterModel,
    ) {
        val updatedState = _uiState.updateAndGet {
            it.copy(registerInfo = transform(it.registerInfo))
        }

        val isBothSelected = updatedState.registerInfo.contactedDate != null &&
            updatedState.registerInfo.contactedTime != null
        if (isBothSelected && !updatedState.isContactDateTimeValid) {
            viewModelScope.launch {
                _sideEffect.send(OnShowToast("현재 시간 이후로는 선택할 수 없어요", isAlarm = false))
            }
        }
    }

    fun onChannelToggled(channel: NotificationChannelType) {
        _uiState.update { it.toggleNotificationChannel(channel) }
    }

    fun onImagesPicked(uris: List<Uri>) {
        if (uris.isEmpty()) return

        viewModelScope.launch {
            val currentState = _uiState.value
            val newUris = uris.distinct()
                .filterNot { uri ->
                    currentState.proofImages.any { it.uri == uri }
                }
            val acceptedUris = newUris.take(currentState.remainingProofImageCount)

            if (newUris.size > acceptedUris.size) showImageLimitToast()

            val newImages = acceptedUris.map { it.toProofImage() }

            _uiState.update { state ->
                state.copy(proofImages = (state.proofImages + newImages).toImmutableList())
            }

            newImages
                .filter { it.error == null }
                .forEach { uploadProofImage(it.uri) }
        }
    }

    private fun uploadProofImage(uri: Uri) {
        viewModelScope.launch {
            registerRepository.postVerificationImage(uri)
                .onSuccess { imageId ->
                    updateProofImage(uri) { it.copy(imageId = imageId) }
                }
                .onFailure { e ->
                    Timber.e(e, "proof image upload failed")
                    updateProofImage(uri) { it.copy(error = RegisterProofImageErrorType.LOAD_FAILED) }
                }
        }
    }

    private fun updateProofImage(
        uri: Uri,
        transform: (RegisterProofImageModel) -> RegisterProofImageModel,
    ) {
        _uiState.update { state ->
            state.copy(
                proofImages = state.proofImages
                    .map { if (it.uri == uri) transform(it) else it }
                    .toImmutableList()
            )
        }
    }

    fun onRemoveImage(uri: Uri) {
        val images = _uiState.value.proofImages
        val target = images.find { it.uri == uri } ?: return
        val isLastValidImage = target.error == null && images.count { it.error == null } == 1

        if (isLastValidImage) {
            viewModelScope.launch {
                _sideEffect.send(OnShowToast("다른 이미지를 추가한 후 삭제해주세요.", isAlarm = false))
            }
            return
        }

        _uiState.update { state ->
            state.copy(
                proofImages = state.proofImages.filterNot { it.uri == uri }.toImmutableList()
            )
        }
    }

    fun onAddImageClick() {
        val remainingCount = _uiState.value.remainingProofImageCount

        viewModelScope.launch {
            when {
                remainingCount <= 0 -> showImageLimitToast()
                remainingCount == 1 -> _sideEffect.send(OpenSingleImagePicker)
                else -> _sideEffect.send(OpenMultipleImagePicker)
            }
        }
    }

    private suspend fun showImageLimitToast() {
        _sideEffect.send(OnShowToast("첨부 가능한 이미지 개수를 초과했어요.", isAlarm = false))
    }

    private suspend fun Uri.toProofImage(): RegisterProofImageModel {
        val info = imageFileInfoReader.read(this)
            ?: return RegisterProofImageModel(
                uri = this,
                fileName = lastPathSegment.orEmpty(),
                fileSize = "",
                error = RegisterProofImageErrorType.LOAD_FAILED,
            )

        return RegisterProofImageModel(
            uri = this,
            fileName = info.name,
            fileSize = info.sizeBytes.toFileSizeText(),
            error = when {
                info.mimeType !in SUPPORTED_IMAGE_MIME_TYPES ->
                    RegisterProofImageErrorType.UNSUPPORTED_FORMAT
                info.sizeBytes > MAX_IMAGE_SIZE_BYTES ->
                    RegisterProofImageErrorType.SIZE_EXCEEDED
                else -> null
            },
        )
    }

    fun onAlarmAgreeToggled(checked: Boolean) {
        _uiState.update {
            it.copy(registerInfo = it.registerInfo.copy(alarmEnabled = checked))
        }
    }

    fun onTermAgreeToggled(checked: Boolean) {
        _uiState.update {
            it.copy(registerInfo = it.registerInfo.copy(anonymous = checked))
        }
    }

    private fun register() {
        val state = _uiState.value
        val selectedResult = state.selectedResult ?: return
        val baseInfo = state.registerInfo.copy(result = selectedResult.toResultType())

        _uiState.update { it.copy(registerUiState = RegisterUiState.Loading) }

        viewModelScope.launch {
            val registerInfo = baseInfo.copy(verificationImageIds = state.uploadedProofImageIds)

            registerRepository.postRegister(registerInfo)
                .onSuccess { registration ->
                    _uiState.update {
                        it.copy(
                            registerInfo = registerInfo,
                            registrationResult = registration,
                            registerUiState = RegisterUiState.Success,
                        ).moveToNextStep(from = RegisterStep.CONFIRM)
                    }
                }
                .onFailure(::onRegisterFailed)
        }
    }

    private fun onRegisterFailed(e: Throwable) {
        Timber.e(e, "register failed")

        _uiState.update {
            it.copy(
                registerUiState = RegisterUiState.Failure(e.message ?: "상태 등록에 실패했습니다.")
            )
        }
    }

    private fun finish() {
        viewModelScope.launch {
            val jobId = route.jobId
            if (jobId != null) {
                _sideEffect.send(NavigateToJobDetail(jobId))
            } else {
                _sideEffect.send(NavigateToHome)
            }
        }
    }
}

private const val MAX_IMAGE_SIZE_BYTES = 10 * 1024 * 1024L
private val SUPPORTED_IMAGE_MIME_TYPES = setOf("image/jpeg", "image/png", "image/heic", "image/heif")

private fun Long.toFileSizeText(): String {
    val kb = this / 1024.0
    return if (kb < 1024) {
        String.format(Locale.US, "%.1fKB", kb)
    } else {
        String.format(Locale.US, "%.1fMB", kb / 1024)
    }
}
