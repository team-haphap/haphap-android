package com.haphap.app.presentation.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.core.designsystem.component.toast.HapHapToast
import com.haphap.app.core.designsystem.component.toast.HapHapToastVisuals
import com.haphap.app.core.designsystem.component.toast.LocalToastTrigger
import com.haphap.app.presentation.main.component.MainBottomBar
import com.haphap.app.presentation.main.component.MainTab
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val TOAST_DURATION = 3000L

@Composable
fun MainScreen(
    appState: MainAppState = rememberMainAppState(),
) {
    val currentTab by appState.currentTab.collectAsStateWithLifecycle()
    val isBottomBarVisible by appState.isBottomBarVisible.collectAsStateWithLifecycle()

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    var job by remember { mutableStateOf<Job?>(null) }

    val onShowToast: (String, Boolean) -> Unit = { message, isAlarm ->
        job?.cancel()
        job = coroutineScope.launch {
            snackbarHostState.currentSnackbarData?.dismiss()

            launch {
                delay(TOAST_DURATION)
                snackbarHostState.currentSnackbarData?.dismiss()
            }

            snackbarHostState.showSnackbar(
                HapHapToastVisuals(
                    message = message,
                    isAlarm = isAlarm,
                )
            )
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
                    val hapHapToastVisuals = data.visuals as? HapHapToastVisuals

                    HapHapToast(
                        text = hapHapToastVisuals.message,
                        isAlarm = hapHapToastVisuals.isAlarm,
                        modifier = Modifier.padding(20.dp),
                    )
                }
            },
        ) { innerPadding ->

            MainNavHost(
                appState = appState,
                innerPadding = innerPadding,
            )
        }
    }
}
