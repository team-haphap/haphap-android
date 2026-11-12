package com.haphap.app.presentation.passcard.storage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.passcard.PassCardRepository
import com.haphap.app.presentation.passcard.storage.PassCardStorageContract.SideEffect.NavigateBack
import com.haphap.app.presentation.passcard.storage.PassCardStorageContract.SideEffect.NavigateToPassCardDetail
import com.haphap.app.presentation.passcard.storage.PassCardStorageContract.SideEffect.NavigateToRegister
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PassCardStorageViewModel @Inject constructor(
    private val passCardRepository: PassCardRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(PassCardStorageContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<PassCardStorageContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        fetchPassCards()
    }

    private fun fetchPassCards() {
        viewModelScope.launch {
            passCardRepository.getPassCardStorage()
                .onSuccess { list ->
                    _uiState.update {
                        it.copy(passCardStorageList = list.toImmutableList())
                    }
                }
                .onFailure { error ->
                    Timber.e("$error 합격카드 보관함 조회에 실패했습니다")
                }
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