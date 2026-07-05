package com.haphap.app.presentation.auth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.auth.LoginRoute
import com.haphap.app.presentation.auth.SignUpCompleteRoute
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
    onLoginSuccess: () -> Unit,
    onSignUpComplete: (userName: String) -> Unit,
) {
    composable<Login> {
        LoginRoute(
            onLoginSuccess = onLoginSuccess,
            onSignUpComplete = onSignUpComplete,
            modifier = Modifier.padding(innerPadding),
            )
    }
}

fun NavGraphBuilder.signUpCompleteGraph(
    innerPadding: PaddingValues,
    onStartClick: () -> Unit,
) {
    composable<SignUpComplete> { backStackEntry ->
        val route = backStackEntry.toRoute<SignUpComplete>()
        SignUpCompleteRoute(
            modifier = Modifier.padding(innerPadding),
            userName = route.userName,
            onStartClick = onStartClick,
        )
    }
}

@Serializable
data object Login: Route

@Serializable
data class SignUpComplete(val userName: String) : Route