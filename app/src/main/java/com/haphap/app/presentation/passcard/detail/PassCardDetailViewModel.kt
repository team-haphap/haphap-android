package com.haphap.app.presentation.passcard.detail

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.haphap.app.core.image.ImageSaver
import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.repository.api.passcard.PassCardRepository
import com.haphap.app.presentation.passcard.detail.PassCardDetailContract.SideEffect.OnShowToast
import com.haphap.app.presentation.passcard.navigation.PassCardDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PassCardDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val passCardRepository: PassCardRepository,
    private val imageSaver: ImageSaver,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PassCardDetailContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<PassCardDetailContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private val registrationId = savedStateHandle.toRoute<PassCardDetail>().id
    private var fetchCardInfoJob: Job? = null

    init {
        fetchCardInfo(registrationId)
    }

    private fun fetchCardInfo(id: Int) {
        fetchCardInfoJob?.cancel()
        fetchCardInfoJob = viewModelScope.launch {
            passCardRepository.getPassCardDetail(id)
                .onSuccess { model ->
                    _uiState.update {
                        it.copy(
                            userName = model.userName,
                            recruitName = model.recruitName,
                            companyName = model.companyName,
                            logoUrl = model.logoUrl,
                            backgroundImageUrl = model.backgroundImageUrl,
                            stageName = model.stageName,
                            chipList = model.stages.toImmutableList(),
                            selectedChip = model.id,
                        )
                    }
                }
                .onFailure { error ->
                    Timber.e("$error 합격카드 상세 조회에 실패했습니다")
                }
        }
    }

    fun onBackgroundLoaded() {
        _uiState.update { it.copy(isBackgroundLoaded = true) }
    }

    fun onLogoLoaded() {
        _uiState.update { it.copy(isLogoLoaded = true) }
    }

    fun savePassCardImage(bitmap: ImageBitmap) {
        viewModelScope.launch {
            suspendRunCatching {
                imageSaver.saveToGallery(
                    bitmap = bitmap.asAndroidBitmap(),
                    fileName = "$FILE_NAME_PREFIX${System.currentTimeMillis()}.png",
                )
            }.onSuccess {
                _sideEffect.send(OnShowToast(message = "합격 카드 저장 완료!"))
            }.onFailure { error ->
                Timber.e("$error 이미지 저장에 실패했습니다")
            }
        }
    }

    fun onStageClick(stageId: Int) {
        if (stageId == _uiState.value.selectedChip) return
        fetchCardInfo(stageId)
    }

    companion object {
        private const val FILE_NAME_PREFIX = "haphap_passcard_"
    }
}
