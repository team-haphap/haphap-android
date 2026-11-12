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
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
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

            _uiState.update { state ->
                if (state.isSubmitting) return@update state

                val acceptedImages = pickedImages
                    .filterNot { picked -> state.newImages.any { it.uri == picked.uri } }
                    .take(state.remainingImageCount)

                state.copy(newImages = (state.newImages + acceptedImages).toImmutableList())
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
        val validImages = state.newImages.filter { !it.isError }
        if (state.isSubmitting || validImages.isEmpty()) return

        _uiState.update { it.copy(isSubmitting = true) }

        viewModelScope.launch {
            uploadImages(validImages)
                .mapCatching { imageIds ->
                    passCertificationRepository.putVerificationImages(registrationId, imageIds).getOrThrow()
                }
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

    private suspend fun uploadImages(images: List<PassCertificationImageModel>): Result<List<Long>> {
        val results = coroutineScope {
            images.map { image ->
                async {
                    image.imageId?.let { Result.success(it) }
                        ?: passCertificationRepository.postVerificationImage(Uri.parse(image.uri))
                            .onSuccess { imageId -> saveUploadedImageId(image.uri, imageId) }
                }
            }.awaitAll()
        }

        results.firstNotNullOfOrNull { it.exceptionOrNull() }?.let { return Result.failure(it) }

        return Result.success(results.map { it.getOrThrow() })
    }

    private fun saveUploadedImageId(uri: String, imageId: Long) {
        _uiState.update { state ->
            state.copy(
                newImages = state.newImages
                    .map { if (it.uri == uri) it.copy(imageId = imageId) else it }
                    .toImmutableList(),
            )
        }
    }

    private suspend fun Uri.toCertificationImage(): PassCertificationImageModel {
        val info = imageFileInfoReader.read(this)
        val isError = info == null ||
            info.mimeType !in SUPPORTED_IMAGE_MIME_TYPES ||
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
