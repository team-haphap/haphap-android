package com.haphap.app.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.core.designsystem.component.toast.HapHapToast
import com.haphap.app.core.designsystem.component.toast.LocalToastTrigger
import com.haphap.app.presentation.main.component.MainBottomBar
import com.haphap.app.presentation.main.component.MainTab
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

private const val TOAST_DURATION = 2000L

@Composable
fun MainScreen(
    appState: MainAppState = rememberMainAppState(),
) {
    val currentTab by appState.currentTab.collectAsStateWithLifecycle()
    val isBottomBarVisible by appState.isBottomBarVisible.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val mutex = remember { Mutex() }

    // 마지막 토스트만 띄우기
    val onShowToast: (String) -> Unit = { message ->
        coroutineScope.launch {
            if (!mutex.tryLock()) return@launch

            try {
                launch {
                    delay(TOAST_DURATION)
                    snackbarHostState.currentSnackbarData?.dismiss()
                }

                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Indefinite,
                )
            } finally {
                mutex.unlock()
            }
        }
    }

    // 모든 토스트 다 띄우기
    val onShowToast2: (String) -> Unit = { message ->
        coroutineScope.launch {
            mutex.withLock {
                launch {
                    delay(TOAST_DURATION)
                    snackbarHostState.currentSnackbarData?.dismiss()
                }

                snackbarHostState.showSnackbar(
                    message = message,
                    duration = SnackbarDuration.Indefinite,
                )
            }
        }
    }

    CompositionLocalProvider(
        LocalToastTrigger provides onShowToast,
    ) {
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
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState) { data ->
                    HapHapToast(
                        text = data.visuals.message,
                        modifier = Modifier.padding(20.dp)
                    )
                }
            },
        ) { innerPadding ->

            MainNavHost(
                appState = appState,
                startDestination = appState.startDestination,
                innerPadding = innerPadding,
            )
        }
    }
}

