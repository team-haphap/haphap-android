package com.haphap.app.presentation.search.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.search.SearchRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToSearch(
    navOptions: NavOptions? = null
) = navigate(Search, navOptions)

fun NavGraphBuilder.searchGraph(
    innerPadding: PaddingValues,
) {
    composable<Search> {
        SearchRoute(
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Search: Route
