package com.haphap.app.presentation.scrap

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.scrap.ScrapRepository
import com.haphap.app.presentation.scrap.ScrapListContract.SideEffect.OnShowToast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ScrapListViewModel @Inject constructor(
    private val scrapRepository: ScrapRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ScrapListContract.State())
    val uiState: StateFlow<ScrapListContract.State> = _uiState.asStateFlow()

    private val _sideEffect = Channel<ScrapListContract.SideEffect>(Channel.BUFFERED)
    val sideEffect = _sideEffect.receiveAsFlow()

    private val scrapMutex = Mutex()
    private val pendingScrapIds = mutableSetOf<Int>()

    fun getScraps() {
        if (_uiState.value.scrapListUiState is ScrapListUiState.Loading) return
        _uiState.update { it.copy(scrapListUiState = ScrapListUiState.Loading) }

        viewModelScope.launch {
            scrapMutex.withLock { fetchScraps() }
        }
    }

    private suspend fun fetchScraps() {
        scrapRepository.getScraps()
            .onSuccess { scraps ->
                _uiState.update {
                    it.copy(
                        scrapList = scraps.toImmutableList(),
                        scrapListUiState = ScrapListUiState.Success,
                    )
                }
            }
            .onFailure { throwable ->
                _uiState.update {
                    it.copy(
                        scrapListUiState = ScrapListUiState.Failure(
                            msg = throwable.message.orEmpty(),
                        ),
                    )
                }
            }
    }

    fun onScrapClick(postingId: Int) {
        if (!pendingScrapIds.add(postingId)) return

        viewModelScope.launch {
            try {
                scrapMutex.withLock { toggleScrap(postingId) }
            } finally {
                pendingScrapIds.remove(postingId)
            }
        }
    }

    private suspend fun toggleScrap(postingId: Int) {
        val target = _uiState.value.scrapList.find { it.postingId == postingId } ?: return

        if (target.isScrapped) {
            scrapRepository.deleteScrap(postingId)
                .onSuccess { isScrapped ->
                    updateScrapped(postingId, isScrapped)
                    _sideEffect.send(OnShowToast(message = "찜한 공고에서 취소했어요."))
                }
                .onFailure { throwable ->
                    Timber.e("$throwable 찜 취소 실패했습니다.")
                }
        } else {
            scrapRepository.postScrap(postingId)
                .onSuccess { isScrapped ->
                    updateScrapped(postingId, isScrapped)
                    _sideEffect.send(OnShowToast(message = "찜한 공고에 추가했어요."))
                }
                .onFailure { throwable ->
                    Timber.e("$throwable 찜하기 실패했습니다.")
                }
        }
    }

    private fun updateScrapped(postingId: Int, isScrapped: Boolean) {
        _uiState.update { state ->
            state.copy(
                scrapList = state.scrapList
                    .map { if (it.postingId == postingId) it.copy(isScrapped = isScrapped) else it }
                    .toImmutableList(),
            )
        }
    }
}
