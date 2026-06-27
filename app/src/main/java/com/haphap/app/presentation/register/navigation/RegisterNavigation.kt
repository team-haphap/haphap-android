package com.haphap.app.presentation.register.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.MainTabRoute
import com.haphap.app.presentation.register.RegisterRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToRegister(
    navOptions: NavOptions? = null
) = navigate(Register, navOptions)

fun NavGraphBuilder.registerGraph(
    innerPadding: PaddingValues,
) {
    composable<Register> {
        RegisterRoute(
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Register: MainTabRoute
