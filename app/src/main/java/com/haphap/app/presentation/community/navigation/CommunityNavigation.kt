package com.haphap.app.presentation.community.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.MainTabRoute
import com.haphap.app.presentation.community.CommunityRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToCommunity(
    navOptions: NavOptions? = null,
) = navigate(Community, navOptions)

fun NavGraphBuilder.communityGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<Community> {
        CommunityRoute(
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Community: MainTabRoute
