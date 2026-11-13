package com.haphap.app.presentation.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.repository.api.mypage.MyPageRepository
import com.haphap.app.presentation.myapplication.type.MyApplicationFilterType
import com.haphap.app.presentation.myapplication.type.toFilter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MyApplicationViewModel @Inject constructor(
    private val myPageRepository: MyPageRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MyApplicationContract.State())
    val uiState = _uiState.asStateFlow()

    private var getMyApplicationsJob: Job? = null

    init {
        getMyApplicationList(MyApplicationFilterType.ALL)
    }

    fun onFilterClick(filter: MyApplicationFilterType) {
        val currentFilter = _uiState.value.selectedFilter
        val newFilter = when {
            filter == MyApplicationFilterType.ALL -> MyApplicationFilterType.ALL
            filter == currentFilter -> MyApplicationFilterType.ALL
            currentFilter != MyApplicationFilterType.ALL -> MyApplicationFilterType.ALL
            else -> filter
        }
        if (newFilter == currentFilter) return

        _uiState.update { it.copy(selectedFilter = newFilter) }
        getMyApplicationList(newFilter)
    }

    private fun getMyApplicationList(filter: MyApplicationFilterType) {
        getMyApplicationsJob?.cancel()
        getMyApplicationsJob = viewModelScope.launch {
            myPageRepository.getMyApplicationList(filter.toFilter())
                .onSuccess { response ->
                    _uiState.update {
                        it.copy(
                            inProgressCount = response.inProgressCount,
                            closedCount = response.closedCount,
                            applicationList = response.applicationList,
                        )
                    }
                }
                .onFailure { error ->
                    Timber.e("내 지원 내역 리스트를 불러오지 못했습니다. $error")
                }
        }
    }
}
