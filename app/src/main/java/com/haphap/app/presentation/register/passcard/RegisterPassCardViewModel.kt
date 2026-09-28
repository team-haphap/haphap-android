package com.haphap.app.presentation.register.passcard

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.toRoute
import com.haphap.app.presentation.register.navigation.RegisterPassCard
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class RegisterPassCardViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val passCard = savedStateHandle.toRoute<RegisterPassCard>()

    private val _uiState = MutableStateFlow(RegisterPassCardContract.State())
    val uiState = _uiState.asStateFlow()

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
}
