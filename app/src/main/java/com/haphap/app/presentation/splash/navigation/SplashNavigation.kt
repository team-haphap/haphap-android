package com.haphap.app.presentation.splash.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.extensions.clearBackStackNavOptions
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.auth.navigation.navigateToLogin
import com.haphap.app.presentation.home.navigation.navigateToHome
import com.haphap.app.presentation.splash.SplashRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSplash(
    navOptions: NavOptions? = null
) = navigate(Splash, navOptions)

fun NavGraphBuilder.splashGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<Splash> {
        SplashRoute(
            navigateToHome = {
                navController.navigateToHome(
                    navOptions = navController.clearBackStackNavOptions(),
                )
            },
            navigateToLogin = {
                navController.navigateToLogin(
                    navOptions = navController.clearBackStackNavOptions(),
                )
            },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Splash : Route
