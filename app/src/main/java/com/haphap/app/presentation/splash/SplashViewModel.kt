package com.haphap.app.presentation.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.core.util.suspendRunCatching
import com.haphap.app.data.local.datasource.api.LocalTokenDataSource
import com.haphap.app.data.model.passcard.PassCardUnconfirmedModel
import com.haphap.app.data.repository.api.passcard.PassCardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val localTokenDataSource: LocalTokenDataSource,
    private val passCardRepository: PassCardRepository,
) : ViewModel() {
    private val _isLoggedIn = MutableStateFlow<Boolean?>(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn

    private val _unconfirmedPassCard = MutableStateFlow<PassCardUnconfirmedModel?>(null)
    val unconfirmedPassCard: StateFlow<PassCardUnconfirmedModel?> = _unconfirmedPassCard

    private val _hasUnconfirmedPassCard = MutableStateFlow(false)
    val hasUnconfirmedPassCard: StateFlow<Boolean> = _hasUnconfirmedPassCard


    init {
        checkLoginState()
    }

    private fun checkLoginState() {
        viewModelScope.launch {
            val tokenDelay = async {
                suspendRunCatching { localTokenDataSource.getAccessToken() }.getOrNull()
            }
            delay(MIN_SPLASH_DELAY_MS)

            val accessToken = tokenDelay.await()
            val isLoggedIn = !accessToken.isNullOrBlank()
            if (isLoggedIn) fetchUnconfirmedPassCards()
            _isLoggedIn.value = isLoggedIn
        }
    }

    private suspend fun fetchUnconfirmedPassCards() {
        passCardRepository.getUnconfirmedPassCards()
            .onSuccess { passCards ->
                val sevenDaysAgo = LocalDateTime.now().minus(RECENT_DAYS, ChronoUnit.DAYS)

                _unconfirmedPassCard.value = passCards
                    .filter { LocalDateTime.parse(it.approvedAt).isAfter(sevenDaysAgo) }
                    .maxByOrNull { LocalDateTime.parse(it.approvedAt) }
                _hasUnconfirmedPassCard.value = passCards.isNotEmpty()
            }
            .onFailure { error ->
                Timber.e("$error 미확인 합격카드 조회에 실패했습니다")
            }
    }

    companion object {
        private const val MIN_SPLASH_DELAY_MS = 2000L
        private const val RECENT_DAYS = 7L
    }
}