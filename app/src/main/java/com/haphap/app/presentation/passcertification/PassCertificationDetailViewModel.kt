package com.haphap.app.presentation.passcertification

import android.net.Uri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.haphap.app.core.image.ImageFileInfoReader
import com.haphap.app.data.repository.api.passcertification.PassCertificationRepository
import com.haphap.app.presentation.passcertification.PassCertificationDetailContract.SideEffect.NavigateToComplete
import com.haphap.app.presentation.passcertification.PassCertificationDetailContract.SideEffect.OnShowToast
import com.haphap.app.presentation.passcertification.model.PassCertificationImageModel
import com.haphap.app.presentation.passcertification.navigation.PassCertificationDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PassCertificationDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val passCertificationRepository: PassCertificationRepository,
    private val imageFileInfoReader: ImageFileInfoReader,
) : ViewModel() {
    private val registrationId: Long = savedStateHandle.toRoute<PassCertificationDetail>().certificationId

    private val _uiState = MutableStateFlow(PassCertificationDetailContract.State())
    val uiState: StateFlow<PassCertificationDetailContract.State> = _uiState.asStateFlow()

    private val _sideEffect = Channel<PassCertificationDetailContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        getCertificationDetail()
    }

    private fun getCertificationDetail() {
        _uiState.update { it.copy(detailUiState = PassCertificationDetailUiState.Loading) }

        viewModelScope.launch {
            passCertificationRepository.getCertificationDetail(registrationId)
                .onSuccess { certification ->
                    _uiState.update {
                        it.copy(
                            certification = certification,
                            detailUiState = PassCertificationDetailUiState.Success,
                        )
                    }
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            detailUiState = PassCertificationDetailUiState.Failure(
                                msg = throwable.message.orEmpty(),
                            ),
                        )
                    }
                }
        }
    }

    fun onSubmitClick() {
        _uiState.update { it.copy(isResubmitMode = true) }
    }

    fun onImagesPicked(uris: List<Uri>) {
        if (_uiState.value.isSubmitting) return

        viewModelScope.launch {
            val pickedImages = uris.distinct().map { it.toCertificationImage() }
            var acceptedImages = emptyList<PassCertificationImageModel>()

            _uiState.update { state ->
                if (state.isSubmitting) return@update state

                acceptedImages = pickedImages
                    .filterNot { picked -> state.newImages.any { it.uri == picked.uri } }
                    .take(state.remainingImageCount)

                state.copy(newImages = (state.newImages + acceptedImages).toImmutableList())
            }

            acceptedImages
                .filterNot { it.isError }
                .forEach { image -> uploadImage(image.uri) }
        }
    }

    private fun uploadImage(uri: String) {
        viewModelScope.launch {
            passCertificationRepository.postVerificationImage(Uri.parse(uri))
                .onSuccess { imageId -> updateImage(uri) { it.copy(imageId = imageId) } }
                .onFailure { throwable ->
                    Timber.e(throwable, "pass certification image upload failed")
                    updateImage(uri) { it.copy(isError = true) }
                }
        }
    }

    fun onDeleteImageClick(index: Int) {
        _uiState.update {
            if (it.isSubmitting || index !in it.newImages.indices) return@update it

            it.copy(
                newImages = it.newImages.toPersistentList().removeAt(index),
            )
        }
    }

    fun onResubmitClick() {
        val state = _uiState.value
        if (state.isSubmitting || !state.isResubmitEnabled) return

        val imageIds = state.newImages
            .filter { !it.isError }
            .mapNotNull { it.imageId }

        _uiState.update { it.copy(isSubmitting = true) }

        viewModelScope.launch {
            passCertificationRepository.putVerificationImages(registrationId, imageIds)
                .onSuccess {
                    _uiState.update { it.copy(isSubmitting = false) }
                    _sideEffect.send(NavigateToComplete)
                }
                .onFailure { throwable ->
                    Timber.e(throwable, "pass certification resubmit failed")
                    _uiState.update { it.copy(isSubmitting = false) }
                    _sideEffect.send(OnShowToast(message = RESUBMIT_FAILED_MESSAGE))
                }
        }
    }

    private fun updateImage(
        uri: String,
        transform: (PassCertificationImageModel) -> PassCertificationImageModel,
    ) {
        _uiState.update { state ->
            state.copy(
                newImages = state.newImages
                    .map { if (it.uri == uri) transform(it) else it }
                    .toImmutableList(),
            )
        }
    }

    private suspend fun Uri.toCertificationImage(): PassCertificationImageModel {
        val info = imageFileInfoReader.read(this)
        val isError = info == null ||
            info.mimeType !in SUPPORTED_IMAGE_MIME_TYPES ||
            info.sizeBytes <= 0 ||
            info.sizeBytes > MAX_IMAGE_SIZE_BYTES

        return PassCertificationImageModel(
            uri = toString(),
            isError = isError,
        )
    }

    companion object {
        private const val RESUBMIT_FAILED_MESSAGE = "제출이 실패했어요. 다시 시도해주세요."
        private const val MAX_IMAGE_SIZE_BYTES = 10 * 1024 * 1024L
        private val SUPPORTED_IMAGE_MIME_TYPES = setOf("image/jpeg", "image/png", "image/heic", "image/heif")
    }
}
