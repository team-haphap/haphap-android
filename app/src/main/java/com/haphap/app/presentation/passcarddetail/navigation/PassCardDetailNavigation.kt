package com.haphap.app.presentation.passcarddetail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.extensions.clearBackStackNavOptions
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.home.navigation.navigateToHome
import com.haphap.app.presentation.passcarddetail.PassCardDetailRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToPassCardDetail(
    id: Int,
    navOptions: NavOptions? = null,
) = navigate(PassCardDetail(id), navOptions)

fun NavGraphBuilder.passCardDetailGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<PassCardDetail> {
        PassCardDetailRoute(
            navigateBack = dropUnlessResumed { navController.popBackStack() },
            navigateToHome = {
                navController.navigateToHome(
                    navOptions = navController.clearBackStackNavOptions()
                )
            },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data class PassCardDetail(val id: Int) : Route