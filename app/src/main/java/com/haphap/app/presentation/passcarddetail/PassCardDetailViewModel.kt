package com.haphap.app.presentation.passcarddetail

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.core.image.ImageSaver
import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.model.passcard.PassCardDetailChipListModel
import com.haphap.app.presentation.passcarddetail.PassCardDetailContract.SideEffect.OnShowToast
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
    private val imageSaver: ImageSaver,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PassCardDetailContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<PassCardDetailContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        fetchCardInfo()
    }

    private fun fetchCardInfo() {
        // TODO: API 연결 시 수정
        val chipList = persistentListOf(
            PassCardDetailChipListModel(id = 101, stageName = "서류"),
            PassCardDetailChipListModel(id = 102, stageName = "인적성"),
            PassCardDetailChipListModel(id = 103, stageName = "코딩테스트"),
        )

        _uiState.update {
            it.copy(
                userName = "김합합",
                recruitName = "2026 신입 카카오 AI 신규 서비스 기획 공개 채용",
                companyName = "카카오",
                logoUrl = "",
                backgroundImageUrl = "",
                chipList = chipList,
                selectedChip = chipList.firstOrNull()?.id ?: 0,
            )
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
            }.onFailure { throwable ->
                Timber.e(throwable, "이미지 저장에 실패했어요")
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
