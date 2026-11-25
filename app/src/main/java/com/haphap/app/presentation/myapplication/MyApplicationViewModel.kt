package com.haphap.app.presentation.myapplication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.data.model.myapplication.MyApplicationFilter
import com.haphap.app.data.repository.api.mypage.MyPageRepository
import com.haphap.app.presentation.myapplication.type.MyApplicationFilterType
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
    private var loadedFilter: MyApplicationFilter? = null

    init {
        getMyApplicationList(_uiState.value.queryFilter)
    }

    fun onFilterClick(filter: MyApplicationFilterType) {
        val currentState = _uiState.value
        val updatedState = currentState.toggle(filter)
        if (updatedState == currentState) return

        _uiState.value = updatedState
        if (updatedState.queryFilter == loadedFilter) {
            getMyApplicationsJob?.cancel()
        } else {
            getMyApplicationList(updatedState.queryFilter)
        }
    }

    private fun getMyApplicationList(filter: MyApplicationFilter) {
        getMyApplicationsJob?.cancel()
        getMyApplicationsJob = viewModelScope.launch {
            myPageRepository.getMyApplicationList(filter)
                .onSuccess { response ->
                    loadedFilter = filter
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
