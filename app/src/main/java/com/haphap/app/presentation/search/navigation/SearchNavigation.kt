package com.haphap.app.presentation.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.jobdetail.navigation.navigateToJobDetail
import com.haphap.app.presentation.search.SearchRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSearch(
    navOptions: NavOptions? = null
) = navigate(Search, navOptions)

fun NavGraphBuilder.searchGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<Search> {
        val layoutDirection = LocalLayoutDirection.current

        SearchRoute(
            navigateBack = { navController.popBackStack() },
            navigateToJobDetail = navController::navigateToJobDetail,
            modifier = Modifier.padding(
                top = innerPadding.calculateTopPadding(),
                start = innerPadding.calculateStartPadding(layoutDirection),
                end = innerPadding.calculateEndPadding(layoutDirection),
            ),
        )
    }
}

@Serializable
data object Search: Route
