package com.haphap.app.presentation.mypage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.mypage.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
        getMyPageSummary()
    }

    fun getMyPageSummary() {
        _uiState.update { it.copy(myPageUiState = MyPageUiState.Loading) }

        viewModelScope.launch {
            myPageRepository.getMyPageSummary()
                .onSuccess { model ->
                    _uiState.update {
                        it.copy(
                            myPageUiState = MyPageUiState.Success,
                            profileUrl = model.profileImageUrl,
                            nickNameText = model.nickname,
                            point = model.point,
                            rejectedCertificationCount = model.rejectedCertificationCount,
                            approvedCertificationCount = model.approvedCertificationCount,
                            hasUpcomingAnnouncement = model.hasUpcomingAnnouncement,
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
    }
}
