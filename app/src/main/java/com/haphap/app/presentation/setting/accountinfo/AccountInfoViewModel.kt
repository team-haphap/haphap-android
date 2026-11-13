package com.haphap.app.presentation.setting.accountinfo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.mypage.MyPageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AccountInfoViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(AccountInfoContract.State())
    val uiState: StateFlow<AccountInfoContract.State> = _uiState.asStateFlow()

    init {
        getAccountInfo()
    }

    private fun getAccountInfo() {
        viewModelScope.launch {
            myPageRepository.getAccountInfo()
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            name = response.name,
                            email = response.email,
                            loginProvider = response.loginProvider
                        )
                    }
                }
                .onFailure { error ->
                    Timber.e("계정 정보를 불러오지 못했습니다. $error")
                }
        }
    }
}
