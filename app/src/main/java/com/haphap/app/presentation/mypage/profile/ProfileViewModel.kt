package com.haphap.app.presentation.mypage.profile

import android.net.Uri
import androidx.compose.foundation.text.input.InputTransformation
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.mypage.MyPageRepository
import com.haphap.app.presentation.mypage.profile.ProfileContract.SideEffect.NavigateBack
import com.haphap.app.presentation.mypage.profile.ProfileContract.SideEffect.OpenImagePicker
import com.haphap.app.presentation.mypage.profile.type.ProfileNicknameStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileContract.State())
    val uiState: StateFlow<ProfileContract.State> = _uiState.asStateFlow()

    private val _sideEffect = Channel<ProfileContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    val nicknameState = TextFieldState()
    private var originalNickname = ""

    val nicknameInputTransformation = InputTransformation {
        if (length > NICKNAME_MAX_LENGTH) {
            revertAllChanges()
            _uiState.update { it.copy(nicknameStatus = ProfileNicknameStatus.TOO_LONG) }
        }
    }

    init {
        getProfile()
        observeNickname()
    }

    private fun getProfile() {
        viewModelScope.launch {
            myPageRepository.getProfile()
                .onSuccess { model ->
                    originalNickname = model.nickname
                    nicknameState.setTextAndPlaceCursorAtEnd(model.nickname)
                    _uiState.update {
                        it.copy(profileImageUrl = model.profileImageUrl)
                    }
                }
                .onFailure { throwable ->
                    Timber.e(throwable)
                }
        }
    }

    private fun observeNickname() {
        viewModelScope.launch {
            snapshotFlow { nicknameState.text.toString() }
                .distinctUntilChanged()
                .drop(1)
                .collect { nickname ->
                    val status = if (NICKNAME_REGEX.matches(nickname)) {
                        ProfileNicknameStatus.DEFAULT
                    } else {
                        ProfileNicknameStatus.INVALID_CHARACTER
                    }
                    _uiState.update { it.copy(nicknameStatus = status) }
                }
        }
    }

    fun onBackClick() {
        viewModelScope.launch {
            _sideEffect.send(NavigateBack)
        }
    }

    fun onImageClick() {
        viewModelScope.launch {
            _sideEffect.send(OpenImagePicker)
        }
    }

    fun onImageSelected(uri: Uri) {
        _uiState.update {
            it.copy(
                selectedImageUri = uri,
                profileImageUrl = uri.toString(),
            )
        }
    }

    fun onSaveClick() {
        // TODO: API 연결 시 교체. 지금은 "중복" 입력하면 에러
        val nickname = nicknameState.text.toString().trim()
        if (nickname == "중복") {
            _uiState.update { it.copy(nicknameStatus = ProfileNicknameStatus.DUPLICATED) }
            return
        }
    }

    companion object {
        private const val NICKNAME_MAX_LENGTH = 5
        private val NICKNAME_REGEX = Regex("^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9]*$")
    }
}