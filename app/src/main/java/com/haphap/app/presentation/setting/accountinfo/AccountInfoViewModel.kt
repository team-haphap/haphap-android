package com.haphap.app.presentation.setting.accountinfo

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AccountInfoViewModel @Inject constructor(
) : ViewModel() {
    private val _uiState = MutableStateFlow(AccountInfoContract.State())
    val uiState: StateFlow<AccountInfoContract.State> = _uiState.asStateFlow()

    init {
        getAccountInfo()
    }

    private fun getAccountInfo() {
    }
}
