package com.haphap.app.presentation.auth.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.auth.LoginRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToLogin(
    navOptions: NavOptions? = null
) = navigate(Login, navOptions)

fun NavGraphBuilder.authGraph(
    innerPadding: PaddingValues,
    onLoginSuccess: () -> Unit,
) {
    composable<Login> {
        LoginRoute(
            modifier = Modifier.padding(innerPadding),
            onLoginSuccess = onLoginSuccess,
        )
    }
}

@Serializable
data object Login: Route