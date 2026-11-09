package com.haphap.app.presentation.mypage.profile

import android.net.Uri
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.foundation.text.input.setTextAndPlaceCursorAtEnd
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import javax.inject.Inject
import com.haphap.app.presentation.mypage.profile.ProfileContract.SideEffect.NavigateBack
import com.haphap.app.presentation.mypage.profile.ProfileContract.SideEffect.OpenImagePicker

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(ProfileContract.State())
    val uiState: StateFlow<ProfileContract.State> = _uiState.asStateFlow()

    private val _sideEffect = Channel<ProfileContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    val nicknameState = TextFieldState()
    private var originalNickname = ""

    init {
        getProfile()
        observeNickname()
    }

    private fun getProfile() {
        originalNickname = "김합합"
        nicknameState.setTextAndPlaceCursorAtEnd(originalNickname)
        _uiState.update {
            it.copy(
                profileImageUrl = "",
            )
        }
    }

    private fun observeNickname() {
        viewModelScope.launch {
            snapshotFlow { nicknameState.text.toString() }
                .distinctUntilChanged()
                .drop(1)
                .collect {
                    _uiState.update { state -> state.copy(isError = false) }
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
            _uiState.update { it.copy(isError = true) }
            return
        }
    }
}