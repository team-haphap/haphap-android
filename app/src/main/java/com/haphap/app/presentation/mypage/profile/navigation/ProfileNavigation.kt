package com.haphap.app.presentation.mypage.profile.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.mypage.profile.ProfileRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToProfile(
    navOptions: NavOptions? = null,
) = navigate(Profile, navOptions)

fun NavGraphBuilder.profileGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<Profile> {
        ProfileRoute(
            navigateBack = navController::popBackStack,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Profile: Route
