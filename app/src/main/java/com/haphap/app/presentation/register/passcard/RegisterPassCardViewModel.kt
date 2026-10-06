package com.haphap.app.presentation.register.passcard

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.haphap.app.core.image.ImageSaver
import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.presentation.register.navigation.RegisterPassCard
import com.haphap.app.presentation.register.passcard.RegisterPassCardContract.SideEffect.OnShowToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RegisterPassCardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val imageSaver: ImageSaver,
) : ViewModel() {

    private val passCard = savedStateHandle.toRoute<RegisterPassCard>()

    private val _uiState = MutableStateFlow(RegisterPassCardContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<RegisterPassCardContract.SideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        fetchCardInfo()
    }

    private fun fetchCardInfo() {
        _uiState.update {
            it.copy(
                userName = passCard.userName,
                recruitName = passCard.recruitName,
                companyName = passCard.companyName,
                logoUrl = passCard.logoUrl,
                backgroundImageUrl = passCard.backgroundImageUrl,
            )
        }
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

    fun onSavePermissionDenied() {
        viewModelScope.launch {
            // _sideEffect.send(OnShowToast(message = "사진 저장 권한이 필요해요"))
        }
    }

    companion object {
        private const val FILE_NAME_PREFIX = "haphap_passcard_"
    }
}
