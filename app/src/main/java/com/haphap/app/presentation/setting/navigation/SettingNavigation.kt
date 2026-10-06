package com.haphap.app.presentation.setting.navigation

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
import com.haphap.app.presentation.leave.navigation.navigateToLeave
import com.haphap.app.presentation.setting.SettingRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSetting(
    navOptions: NavOptions? = null,
) = navigate(Setting, navOptions)

fun NavGraphBuilder.settingGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<Setting> {
        SettingRoute(
            navigateBack = navController::popBackStack,
            navigateToLeave = navController::navigateToLeave,
            navigateToLogin = {
                navController.navigateToLogin(
                    navOptions = navController.clearBackStackNavOptions()
                )
            },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Setting : Route
