package com.haphap.app.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.home.HomeRepository
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
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeContract.State())
    val uiState = _uiState.asStateFlow()

    private val _sideEffect = Channel<HomeContract.SideEffect>()
    val sideEffect = _sideEffect.receiveAsFlow()

    init {
        fetchAll()
    }

    private fun fetchAll() {
        fetchBannerList()
        fetchPopularPostings()
    }

    private fun fetchBannerList() {
        viewModelScope.launch {
            homeRepository.getBannerList()
                .onSuccess { banners ->
                    _uiState.update { it.copy(bannerList = banners.toImmutableList()) }
                }
                .onFailure { e ->
                    Timber.e(e, "배너 목록 조회 실패")
                }
        }
    }

    private fun fetchMyApplications() {
        viewModelScope.launch {
            homeRepository.getMyApplications()
                .onSuccess { list ->
                    _uiState.update {
                        it.copy(myApplicationCardList = list.toImmutableList())
                    }
                }
                .onFailure { e ->
                    Timber.e(e, "내 지원 조회 실패")
                }
        }
    }

    private fun fetchRecentViews() {
        viewModelScope.launch {
            homeRepository.getRecentViews()
                .onSuccess { list ->
                    _uiState.update {
                        it.copy(recentJobCardList = list.toImmutableList())
                    }
                }
                .onFailure { e ->
                    Timber.e(e, "최근 조회한 공고 조회 실패")
                }
        }
    }

    private fun fetchPopularPostings() {
        val categoryParam = _uiState.value.categoryChipState.queryCategoryList

        viewModelScope.launch {
            homeRepository.getPopularPostings(categoryParam)
                .onSuccess { list ->
                    if (_uiState.value.categoryChipState.queryCategoryList == categoryParam) {
                        _uiState.update { it.copy(popularJobCardList = list.toImmutableList()) }
                    }
                }
                .onFailure { e ->
                    Timber.e(e, "인기 공고 조회 실패")
                }
        }
    }

    fun updateSelectedChips(category: String) {
        _uiState.update {
            it.copy(categoryChipState = it.categoryChipState.toggle(category))
        }
        fetchPopularPostings()
    }

    fun onSearchBarClick() = viewModelScope.launch {
        _sideEffect.send(HomeContract.SideEffect.NavigateToSearch)
    }

    fun onCardClick(postingId: Int) = viewModelScope.launch {
        _sideEffect.send(HomeContract.SideEffect.NavigateToJobDetail(postingId))
    }

    fun onBannerClick(linkUrl: String?) {
        val url = linkUrl ?: return
        viewModelScope.launch {
            _sideEffect.send(HomeContract.SideEffect.OpenUrl(url))
        }
    }

    fun refreshUserSections() {
        fetchMyApplications()
        fetchRecentViews()
    }
}
