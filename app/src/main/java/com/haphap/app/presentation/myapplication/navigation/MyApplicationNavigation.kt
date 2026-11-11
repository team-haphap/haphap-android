package com.haphap.app.presentation.myapplication.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.myapplication.MyApplicationRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToMyApplication(
    navOptions: NavOptions? = null,
) = navigate(MyApplication, navOptions)

fun NavGraphBuilder.myApplicationGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<MyApplication> {
        MyApplicationRoute(
            navigateBack = navController::popBackStack,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object MyApplication : Route
