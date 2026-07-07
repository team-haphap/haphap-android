package com.haphap.app.presentation.splash.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.splash.SplashRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSplash(
    navOptions: NavOptions? = null
) = navigate(Splash, navOptions)

fun NavGraphBuilder.splashGraph(
    innerPadding: PaddingValues,
    navigateToHome: () -> Unit,
    navigateToLogin: () -> Unit,
) {
    composable<Splash> {
        SplashRoute (
            navigateToHome = navigateToHome,
            navigateToLogin = navigateToLogin,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Splash : Route