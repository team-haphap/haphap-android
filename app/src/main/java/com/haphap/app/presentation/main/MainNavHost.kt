package com.haphap.app.presentation.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.haphap.app.presentation.calendar.navigation.calendarGraph
import com.haphap.app.presentation.home.navigation.homeGraph
import com.haphap.app.presentation.job.navigation.jobGraph
import com.haphap.app.presentation.mypage.navigation.myPageGraph
import com.haphap.app.presentation.register.navigation.registerGraph

@Composable
fun MainNavHost(
    appState: MainAppState,
    innerPadding: PaddingValues,
) {
    val navController = appState.navController

    NavHost(
        navController = navController,
        startDestination = appState.startDestination,
    ) {

        homeGraph(
            innerPadding = innerPadding,
        )

        jobGraph(
            innerPadding = innerPadding,
        )

        registerGraph(
            innerPadding = innerPadding,
        )

        calendarGraph(
            innerPadding = innerPadding,
        )

        myPageGraph(
            innerPadding = innerPadding,
        )

    }
}
