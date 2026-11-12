package com.haphap.app.presentation.passcertification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.passcertification.PassCertificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PassCertificationCompleteViewModel @Inject constructor(
    private val passCertificationRepository: PassCertificationRepository,
) : ViewModel() {
    private val _sideEffect = Channel<SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    private var isChecking = false

    fun onCompleteClick() {
        if (isChecking) return
        isChecking = true

        viewModelScope.launch {
            val hasRemaining = passCertificationRepository.getRejectedCertifications()
                .map { it.isNotEmpty() }
                .getOrDefault(true)

            _sideEffect.send(if (hasRemaining) SideEffect.NavigateToList else SideEffect.NavigateToMyPage)
            isChecking = false
        }
    }

    sealed interface SideEffect {
        data object NavigateToList : SideEffect
        data object NavigateToMyPage : SideEffect
    }
}
