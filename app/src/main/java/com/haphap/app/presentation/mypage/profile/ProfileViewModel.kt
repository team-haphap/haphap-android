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
import com.haphap.app.presentation.mypage.profile.ProfileContract.SideEffect.ShowToast
import com.haphap.app.presentation.mypage.profile.type.ProfileNicknameStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
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
                .onFailure { error ->
                    Timber.e("$error 프로필 조회에 실패했습니다.")
                }
        }
    }

    private fun observeNickname() {
        viewModelScope.launch {
            snapshotFlow { nicknameState.text.toString() }
                .distinctUntilChanged()
                .drop(1)
                .collectLatest { nickname ->
                    updateNicknameState(nickname)

                    if (nickname == originalNickname || !NICKNAME_COMPLETE_REGEX.matches(nickname)) {
                        return@collectLatest
                    }

                    delay(NICKNAME_DEBOUNCE_MILLIS)
                    validateNickname(nickname)
                }
        }
    }

    private fun updateNicknameState(nickname: String) {
        val status = when {
            !NICKNAME_REGEX.matches(nickname) -> ProfileNicknameStatus.INVALID_CHARACTER
            nickname.isNotEmpty() && nickname.length < NICKNAME_MIN_LENGTH -> ProfileNicknameStatus.TOO_SHORT
            else -> ProfileNicknameStatus.DEFAULT
        }

        _uiState.update {
            it.copy(
                nicknameStatus = status,
                isNicknameChanged = nickname != originalNickname,
                isNicknameValid = NICKNAME_COMPLETE_REGEX.matches(nickname),
            )
        }
    }

    private suspend fun validateNickname(nickname: String) {
        myPageRepository.validateNickname(nickname)
            .onSuccess { isAvailable ->
                if (!isAvailable) {
                    _uiState.update { it.copy(nicknameStatus = ProfileNicknameStatus.DUPLICATED) }
                }
            }
            .onFailure { error ->
                Timber.e("$error 닉네임 검증에 실패했습니다.")
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
        val nickname = nicknameState.text.toString()

        viewModelScope.launch {
            myPageRepository.updateProfile(
                nickname = nickname.takeIf { it != originalNickname },
                profileImageUri = _uiState.value.selectedImageUri,
            )
                .onSuccess {
                    originalNickname = nickname
                    _uiState.update {
                        it.copy(
                            selectedImageUri = null,
                            isNicknameChanged = false,
                        )
                    }
                    _sideEffect.send(ShowToast("변경사항이 저장됐어요."))
                }
                .onFailure { error ->
                    Timber.e("$error 프로필 저장에 실패했습니다.")
                    _sideEffect.send(ShowToast("변경사항 저장에 실패했어요. 다시 시도해주세요."))
                }
        }
    }

    companion object {
        private const val NICKNAME_MAX_LENGTH = 5
        private const val NICKNAME_MIN_LENGTH = 2
        private const val NICKNAME_DEBOUNCE_MILLIS = 500L
        private val NICKNAME_REGEX = Regex("^[가-힣ㄱ-ㅎㅏ-ㅣa-zA-Z0-9\\u318D\\u119E\\u11A2\\u00B7\\u2025]*$")
        private val NICKNAME_COMPLETE_REGEX = Regex("^[가-힣a-zA-Z0-9]{2,5}$")
    }
}