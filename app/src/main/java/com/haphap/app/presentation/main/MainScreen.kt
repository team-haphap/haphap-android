package com.haphap.app.presentation.main

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.core.designsystem.component.toast.HapHapToast
import com.haphap.app.core.extensions.clearBackStackNavOptions
import com.haphap.app.core.designsystem.component.toast.HapHapToastVisuals
import com.haphap.app.core.designsystem.component.toast.LocalToastBottomInset
import com.haphap.app.core.designsystem.component.toast.LocalToastTrigger
import com.haphap.app.presentation.auth.navigation.navigateToLogin
import com.haphap.app.presentation.jobdetail.navigation.navigateToJobDetail
import com.haphap.app.presentation.main.component.MainBottomBar
import com.haphap.app.presentation.main.component.MainTab
import kotlinx.collections.immutable.toPersistentList
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch

private const val TOAST_DURATION = 3000L

@Composable
fun MainScreen(
    appState: MainAppState = rememberMainAppState(),
    extractedPostingId: Int? = null,
    resetExtractedPostingId: () -> Unit = {},
    sessionExpiredEvent: Flow<Unit> = emptyFlow(),
) {
    val currentTab by appState.currentTab.collectAsStateWithLifecycle()
    val isBottomBarVisible by appState.isBottomBarVisible.collectAsStateWithLifecycle()
    val isHomeTab = currentTab == MainTab.HOME

    LaunchedEffect(extractedPostingId, currentTab) {
        if (extractedPostingId != null && currentTab != null) {
            appState.navController.navigateToJobDetail(extractedPostingId)
            resetExtractedPostingId()
        }
    }

    LaunchedEffect(sessionExpiredEvent) {
        sessionExpiredEvent.collect {
            appState.navController.navigateToLogin(
                navOptions = appState.navController.clearBackStackNavOptions(),
            )
        }
    }

    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    val activity = LocalActivity.current

    BackHandler(enabled = currentTab != null) {
        if (isHomeTab) {
            activity?.finish()
        } else {
            appState.navigate(MainTab.HOME)
        }
    }

    var job by remember { mutableStateOf<Job?>(null) }
    val toastBottomInset = remember { mutableStateOf(0.dp) }
    var toastDisplayBottomInset by remember { mutableStateOf(0.dp) }
    var bottomBarHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val layoutDirection = LocalLayoutDirection.current

    val onShowToast: (String, Boolean) -> Unit = { message, isAlarm ->
        job?.cancel()
        toastDisplayBottomInset = toastBottomInset.value
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
        LocalToastBottomInset provides toastBottomInset,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                modifier = Modifier
                    .fillMaxSize()
                    .navigationBarsPadding(),
                bottomBar = {
                    MainBottomBar(
                        isVisible = isBottomBarVisible,
                        tabs = MainTab.entries.toPersistentList(),
                        currentTab = currentTab,
                        onTabSelected = appState::navigate,
                        modifier = Modifier.onSizeChanged {
                            if (isBottomBarVisible) {
                                bottomBarHeight = with(density) { it.height.toDp() }
                            }
                        }
                    )
                },
            ) { innerPadding ->
                val contentPadding = PaddingValues(
                    start = innerPadding.calculateStartPadding(layoutDirection),
                    top = innerPadding.calculateTopPadding(),
                    end = innerPadding.calculateEndPadding(layoutDirection),
                    bottom = if (isBottomBarVisible) bottomBarHeight else 0.dp
                )

                MainNavHost(
                    appState = appState,
                    innerPadding = contentPadding,
                )
            }

            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding(),
            ) { data ->
                val hapHapToastVisuals = data.visuals as HapHapToastVisuals

                HapHapToast(
                    text = hapHapToastVisuals.message,
                    isAlarm = hapHapToastVisuals.isAlarm,
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 20.dp)
                        .padding(
                            bottom = if (toastDisplayBottomInset > 20.dp) {
                                toastDisplayBottomInset
                            } else {
                                0.dp
                            },
                        ),
                )
            }
        }
    }
}
