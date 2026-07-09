package com.haphap.app.presentation.main

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.navOptions
import com.haphap.app.presentation.auth.navigation.Login
import com.haphap.app.presentation.auth.navigation.SignUpComplete
import com.haphap.app.presentation.auth.navigation.authGraph
import com.haphap.app.presentation.auth.navigation.navigateToSignUpComplete
import com.haphap.app.presentation.auth.navigation.signUpCompleteGraph
import com.haphap.app.presentation.calendar.navigation.calendarGraph
import com.haphap.app.presentation.home.navigation.homeGraph
import com.haphap.app.presentation.home.navigation.navigateToHome
import com.haphap.app.presentation.job.navigation.jobGraph
import com.haphap.app.presentation.mypage.navigation.myPageGraph
import com.haphap.app.presentation.register.navigation.registerGraph
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
            navigateToHome = {
                navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo<Login> {inclusive = true}
                        launchSingleTop = true
                    }
                )
            },
            navigateToSignUpComplete = { userName ->
                navController.navigateToSignUpComplete(
                    userName = userName,
                    navOptions = navOptions {
                        popUpTo<Login> {inclusive = true}
                        launchSingleTop = true
                    }
                )
            }
        )

        signUpCompleteGraph(
            innerPadding = innerPadding,
            navigateToHome = {
                navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo<SignUpComplete> { inclusive = true }
                        launchSingleTop = true
                    }
                )
            }
        )

        homeGraph(
            innerPadding = innerPadding,
        )

        jobGraph(
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

    }
}
