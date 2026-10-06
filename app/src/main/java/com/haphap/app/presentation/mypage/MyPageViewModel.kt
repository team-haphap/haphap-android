package com.haphap.app.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.mypage.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import com.haphap.app.presentation.mypage.MyPageContract.SideEffect.NavigateToHome
import com.haphap.app.presentation.mypage.MyPageContract.SideEffect.NavigateToSetting
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyPageContract.State())
    val uiState: StateFlow<MyPageContract.State> = _uiState.asStateFlow()

    private val _sideEffect = Channel<MyPageContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        getMyPage()
    }

    fun getMyPage() {
        viewModelScope.launch {
            myPageRepository.getMyPage()
                .onSuccess { model ->
                    _uiState.update {
                        it.copy(
                            myPageUiState = MyPageUiState.Success,
                            profileUrl = model.profileImageUrl,
                            nameText = model.name,
                            nickNameText = model.anonymousName,
                            emailText = model.email,
                        )
                    }
                }
                .onFailure { throwable ->
                    Timber.e(throwable)
                    _uiState.update {
                        it.copy(
                            myPageUiState = MyPageUiState.Failure(
                                throwable.message ?: "오류"
                            )
                        )
                    }
                }
        }

        _uiState.update { it.copy(myPageUiState = MyPageUiState.Loading) }
    }

    fun onBackClick() {
        viewModelScope.launch {
            _sideEffect.send(NavigateToHome)
        }
    }

    fun onSettingClick() {
        viewModelScope.launch {
            _sideEffect.send(NavigateToSetting)
        }
    }
}