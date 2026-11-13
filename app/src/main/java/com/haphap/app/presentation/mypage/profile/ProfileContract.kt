package com.haphap.app.presentation.mypage.profile

import android.net.Uri
import androidx.compose.runtime.Immutable
import com.haphap.app.presentation.mypage.profile.type.ProfileNicknameStatus

sealed interface ProfileContract {
    @Immutable
    data class State(
        val profileImageUrl: String = "",
        val selectedImageUri: Uri? = null,
        val nicknameStatus: ProfileNicknameStatus = ProfileNicknameStatus.DEFAULT,
        val isNicknameChanged: Boolean = false,
        val isNicknameValid: Boolean = false,
        val isNicknameNotDuplicated: Boolean = true,
    ) {
        val isSaveEnabled: Boolean
            get() = (isNicknameChanged || selectedImageUri != null) &&
                    isNicknameValid &&
                    isNicknameNotDuplicated
    }

    sealed class SideEffect {
        data object NavigateBack : SideEffect()
        data object OpenImagePicker : SideEffect()
        data class ShowToast(val msg: String) : SideEffect()
    }
}