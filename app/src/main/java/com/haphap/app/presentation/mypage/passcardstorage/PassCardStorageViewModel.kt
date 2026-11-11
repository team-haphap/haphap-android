package com.haphap.app.presentation.mypage.passcardstorage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.model.mypage.PassCardStorageModel
import com.haphap.app.presentation.mypage.passcardstorage.PassCardStorageContract.SideEffect.NavigateBack
import com.haphap.app.presentation.mypage.passcardstorage.PassCardStorageContract.SideEffect.NavigateToPassCardDetail
import com.haphap.app.presentation.mypage.passcardstorage.PassCardStorageContract.SideEffect.NavigateToRegister
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PassCardStorageViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PassCardStorageContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<PassCardStorageContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        fetchPassCards()
    }

    private fun fetchPassCards() {
        _uiState.update {
            it.copy(
                passCardStorageList = persistentListOf(
                    PassCardStorageModel(
                        id = 1,
                        imageUrl = "",
                        companyName = "카카오",
                        category = "개발/데이터",
                        title = "AI 서비스 기획",
                    ),
                    PassCardStorageModel(
                        id = 2,
                        imageUrl = "",
                        companyName = "카카오",
                        category = "개발/데이터",
                        title = "AI 서비스 기획",
                    ),
                ),
            )
        }
    }

    fun onBackClick() = viewModelScope.launch {
        _sideEffect.send(NavigateBack)
    }

    fun onRegisterClick() = viewModelScope.launch {
        _sideEffect.send(NavigateToRegister)
    }

    fun onPassCardClick(id: Int) = viewModelScope.launch {
        _sideEffect.send(NavigateToPassCardDetail(id))
    }
}