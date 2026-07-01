package com.haphap.app.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.presentation.main.component.MainBottomBar
import com.haphap.app.presentation.main.component.MainTab
import kotlinx.collections.immutable.toPersistentList

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel(),
    appState: MainAppState = rememberMainAppState(),
) {
    val startDestination by viewModel.startDestination.collectAsStateWithLifecycle()
    val currentTab by appState.currentTab.collectAsStateWithLifecycle()
    val isBottomBarVisible by appState.isBottomBarVisible.collectAsStateWithLifecycle()

    val destination = startDestination ?: return

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MainBottomBar(
                isVisible = isBottomBarVisible,
                tabs = MainTab.entries.toPersistentList(),
                currentTab = currentTab,
                onTabSelected = appState::navigate,
                modifier = Modifier.navigationBarsPadding()
            )
        },
    ) { innerPadding ->

        MainNavHost(
            appState = appState,
            startDestination = destination,
            innerPadding = innerPadding,
        )
    }
}
