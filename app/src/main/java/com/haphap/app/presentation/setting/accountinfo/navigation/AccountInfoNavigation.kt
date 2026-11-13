package com.haphap.app.presentation.setting.accountinfo.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.setting.accountinfo.AccountInfoRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToAccountInfo(
    navOptions: NavOptions? = null,
) = navigate(AccountInfo, navOptions)

fun NavGraphBuilder.accountInfoGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<AccountInfo> {
        AccountInfoRoute(
            navigateBack = navController::popBackStack,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object AccountInfo : Route
