package com.haphap.app.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.auth.navigation.Login
import dagger.hilt.android.lifecycle.HiltViewModel
import jakarta.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor() : ViewModel() {

    private val _startDestination = MutableStateFlow<Route?>(null)
    val startDestination: StateFlow<Route?> = _startDestination.asStateFlow()

    init {
        viewModelScope.launch {
            _startDestination.value = Login
        }
    }
}