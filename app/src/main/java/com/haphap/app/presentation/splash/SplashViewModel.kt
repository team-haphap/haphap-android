package com.haphap.app.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.local.datasource.api.LocalTokenDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val localTokenDataSource: LocalTokenDataSource
) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    init {
        checkLoginState()
    }

    private fun checkLoginState() {
        viewModelScope.launch {
            val tokenDelayed = async { localTokenDataSource.getAccessToken() }
            val minDelay = async { delay(2000) }

            val accessToken = tokenDelayed.await()
            minDelay.await()

            _isLoggedIn.value = !accessToken.isNullOrBlank()
        }
    }
}