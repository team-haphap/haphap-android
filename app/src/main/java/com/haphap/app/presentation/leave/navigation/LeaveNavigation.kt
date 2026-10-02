package com.haphap.app.presentation.leave.navigation

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
import com.haphap.app.presentation.leave.LeaveCompleteRoute
import com.haphap.app.presentation.leave.LeaveRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToLeave(
    navOptions: NavOptions? = null,
) = navigate(Leave, navOptions)

fun NavController.navigateToLeaveComplete(
    navOptions: NavOptions? = null,
) = navigate(LeaveComplete, navOptions)

fun NavGraphBuilder.leaveGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<Leave> {
        LeaveRoute(
            navigateBack = { navController.popBackStack() },
            navigateToLeaveComplete = {
                navController.navigateToLeaveComplete(
                    navOptions = navController.clearBackStackNavOptions(),
                )
            },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

fun NavGraphBuilder.leaveCompleteGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<LeaveComplete> {
        LeaveCompleteRoute(
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
data object Leave : Route

@Serializable
data object LeaveComplete : Route
