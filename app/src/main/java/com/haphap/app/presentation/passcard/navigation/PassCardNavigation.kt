package com.haphap.app.presentation.passcard.navigation

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
import com.haphap.app.presentation.passcard.detail.PassCardDetailRoute
import com.haphap.app.presentation.passcard.storage.PassCardStorageRoute
import com.haphap.app.presentation.register.navigation.navigateToRegister
import kotlinx.serialization.Serializable

fun NavController.navigateToPassCardStorage(
    navOptions: NavOptions? = null,
) = navigate(PassCardStorage, navOptions)

fun NavController.navigateToPassCardDetail(
    id: Int,
    navOptions: NavOptions? = null,
) = navigate(PassCardDetail(id), navOptions)

fun NavGraphBuilder.passCardGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<PassCardStorage> {
        PassCardStorageRoute(
            navigateBack = dropUnlessResumed { navController.popBackStack() },
            navigateToRegister = navController::navigateToRegister,
            navigateToPassCardDetail = { id ->
                navController.navigateToPassCardDetail(id)
            },
            modifier = Modifier.padding(innerPadding),
        )
    }

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
data object PassCardStorage : Route

@Serializable
data class PassCardDetail(val id: Int) : Route