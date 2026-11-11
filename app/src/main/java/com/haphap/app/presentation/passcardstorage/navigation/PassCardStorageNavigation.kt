package com.haphap.app.presentation.passcardstorage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.passcarddetail.navigation.navigateToPassCardDetail
import com.haphap.app.presentation.passcardstorage.PassCardStorageRoute
import com.haphap.app.presentation.register.navigation.navigateToRegister
import kotlinx.serialization.Serializable

fun NavController.navigateToPassCardStorage(
    navOptions: NavOptions? = null,
) = navigate(PassCardStorage, navOptions)

fun NavGraphBuilder.passCardStorageGraph(
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
}

@Serializable
data object PassCardStorage : Route