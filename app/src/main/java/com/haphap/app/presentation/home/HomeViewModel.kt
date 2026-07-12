package com.haphap.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.home.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeContract.State())
    val uiState = _uiState.asStateFlow()

    init {
        fetchBannerList()
        fetchCountCard()
    }

    private fun fetchBannerList() {
        viewModelScope.launch {
            homeRepository.getBannerList()
                .onSuccess { banners ->
                    _uiState.update { it.copy(bannerList = banners.toPersistentList()) }
                }
                .onFailure { e ->
                    Timber.e(e, "배너 리스트 조회 실패")
                }
        }
    }

    private fun fetchCountCard() {
        viewModelScope.launch {
            homeRepository.getCountCard()
                .onSuccess { count ->
                    _uiState.update { it.copy(countCardModel = count) }
                }
                .onFailure { e ->
                    Timber.e(e, "카운트카드 조회 실패")
                }
        }
    }

    fun updateSelectedChips(category: String){
        _uiState.update {
            it.copy(categoryChipState = it.categoryChipState.toggle(category))
        }
    }
}
