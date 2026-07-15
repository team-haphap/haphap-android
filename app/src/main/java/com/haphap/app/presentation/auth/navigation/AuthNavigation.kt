package com.haphap.app.presentation.auth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.haphap.app.core.extensions.clearBackStackNavOptions
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.auth.login.LoginRoute
import com.haphap.app.presentation.auth.signupcomplete.SignUpCompleteRoute
import com.haphap.app.presentation.home.navigation.navigateToHome
import kotlinx.serialization.Serializable

fun NavController.navigateToLogin(
    navOptions: NavOptions? = null
) = navigate(Login, navOptions)

fun NavController.navigateToSignUpComplete(
    userName: String,
    navOptions: NavOptions? = null,
) = navigate(SignUpComplete(userName), navOptions)

fun NavGraphBuilder.authGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<Login> {
        LoginRoute(
            navigateToSignUpComplete = { userName ->
                navController.navigateToSignUpComplete(
                    userName = userName,
                    navOptions = navController.clearBackStackNavOptions(),
                )
            },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

fun NavGraphBuilder.signUpCompleteGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<SignUpComplete> { backStackEntry ->
        val route = backStackEntry.toRoute<SignUpComplete>()
        SignUpCompleteRoute(
            userName = route.userName,
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
data object Login: Route

@Serializable
data class SignUpComplete(val userName: String) : Route
