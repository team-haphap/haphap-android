package com.haphap.app.presentation.passcard.detail

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.haphap.app.core.image.ImageSaver
import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.model.passcard.PassCardDetailChipListModel
import com.haphap.app.data.repository.api.passcard.PassCardRepository
import com.haphap.app.presentation.passcard.detail.PassCardDetailContract.SideEffect.OnShowToast
import com.haphap.app.presentation.passcard.navigation.PassCardDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
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

    init {
        fetchCardInfo()
    }

    private fun fetchCardInfo() {
        viewModelScope.launch {
            passCardRepository.getPassCardDetail(registrationId)
                .onSuccess { model ->
                    // TODO: 전형 칩 목록 서버에서 받게 되면 교체
                    val chipList = persistentListOf(
                        PassCardDetailChipListModel(id = 101, stageName = "서류"),
                        PassCardDetailChipListModel(id = 102, stageName = "인적성"),
                        PassCardDetailChipListModel(id = 103, stageName = "코딩테스트"),
                    )

                    _uiState.update {
                        it.copy(
                            userName = model.userName,
                            recruitName = model.recruitName,
                            companyName = model.companyName,
                            logoUrl = model.logoUrl,
                            backgroundImageUrl = model.backgroundImageUrl,
                            chipList = chipList,
                            selectedChip = chipList.firstOrNull()?.id ?: 0,
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
                Timber.e(error, "이미지 저장에 실패했습니다.")
            }
        }
    }

    fun onStageClick(stageId: Int) {
        _uiState.update {
            it.copy(selectedChip = stageId)
        }
    }

    companion object {
        private const val FILE_NAME_PREFIX = "haphap_passcard_"
    }
}
