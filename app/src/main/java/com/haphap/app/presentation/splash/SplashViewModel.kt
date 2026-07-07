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
            val tokenDelay = async {
                runCatching { localTokenDataSource.getAccessToken() }.getOrNull()
            }
            delay(MIN_SPLASH_DELAY_MS)

            val accessToken = tokenDelay.await()
            _isLoggedIn.value = !accessToken.isNullOrBlank()
        }
    }
    companion object {
        private const val MIN_SPLASH_DELAY_MS = 2000L
    }
}