package com.haphap.app.presentation.scrap.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.jobdetail.navigation.navigateToJobDetail
import com.haphap.app.presentation.scrap.ScrapListRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToScrapList(
    navOptions: NavOptions? = null,
) = navigate(ScrapList, navOptions)

fun NavGraphBuilder.scrapListGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<ScrapList> {
        ScrapListRoute(
            navigateBack = dropUnlessResumed { navController.popBackStack() },
            navigateToJobDetail = { postingId ->
                navController.navigateToJobDetail(postingId = postingId)
            },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object ScrapList : Route
