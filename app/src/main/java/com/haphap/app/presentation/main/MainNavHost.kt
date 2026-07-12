package com.haphap.app.presentation.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import com.haphap.app.presentation.auth.navigation.authGraph
import com.haphap.app.presentation.auth.navigation.signUpCompleteGraph
import com.haphap.app.presentation.calendar.navigation.calendarGraph
import com.haphap.app.presentation.home.navigation.homeGraph
import com.haphap.app.presentation.joblist.navigation.jobListGraph
import com.haphap.app.presentation.mypage.navigation.myPageGraph
import com.haphap.app.presentation.register.navigation.registerGraph
import com.haphap.app.presentation.search.navigation.searchGraph
import com.haphap.app.presentation.splash.navigation.splashGraph

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
        splashGraph(
            innerPadding = innerPadding,
            navController = navController,
        )

        authGraph(
            innerPadding = innerPadding,
            navController = navController,
        )

        signUpCompleteGraph(
            innerPadding = innerPadding,
            navController = navController,
        )

        homeGraph(
            innerPadding = innerPadding,
        )

        jobListGraph(
            innerPadding = innerPadding,
        )

        registerGraph(
            innerPadding = innerPadding,
            navController = navController,
        )

        calendarGraph(
            innerPadding = innerPadding,
        )

        myPageGraph(
            innerPadding = innerPadding,
        )

        searchGraph(
            innerPadding = innerPadding,
        )

    }
}
