package com.haphap.app.presentation.register.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.haphap.app.core.navigation.MainTabRoute
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.register.RegisterEntryPoint
import com.haphap.app.presentation.register.RegisterRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToRegister(
    navOptions: NavOptions? = null
) = navigate(Register, navOptions)

fun NavController.navigateToRegisterFromJobDetail(
    jobId: Long,
    navOptions: NavOptions? = null,
) = navigate(RegisterFromJobDetail(jobId), navOptions)

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.registerGraph(
    innerPadding: PaddingValues,
    navController: NavController,
    navigateToHome: () -> Unit,
    navigateToJobDetail: (jobId: Long) -> Unit,
) {
    composable<Register> {
        RegisterRoute(
            entryPoint = RegisterEntryPoint.Home,
            navigateBack = { navController.popBackStack() },
            navigateToHome = navigateToHome,
            navigateToJobDetail = navigateToJobDetail,
            modifier = Modifier.padding(innerPadding),
        )
    }

    composable<RegisterFromJobDetail> { backStackEntry ->
        val route = backStackEntry.toRoute<RegisterFromJobDetail>()

        RegisterRoute(
            entryPoint = RegisterEntryPoint.JobDetail(jobId = route.jobId),
            navigateBack = { navController.popBackStack() },
            navigateToHome = navigateToHome,
            navigateToJobDetail = navigateToJobDetail,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Register: MainTabRoute

@Serializable
data class RegisterFromJobDetail(val jobId: Long) : Route