package com.haphap.app.presentation.home.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.MainTabRoute
import com.haphap.app.presentation.home.HomeRoute
import com.haphap.app.presentation.search.navigation.navigateToSearch
import kotlinx.serialization.Serializable

fun NavController.navigateToHome(
    navOptions: NavOptions? = null
) = navigate(Home, navOptions)

fun NavGraphBuilder.homeGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<Home> {
        HomeRoute(
            modifier = Modifier.padding(innerPadding),
            navigateToSearch = navController::navigateToSearch
        )
    }
}

@Serializable
data object Home: MainTabRoute
