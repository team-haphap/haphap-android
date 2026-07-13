package com.haphap.app.presentation.jobdetail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.jobdetail.JobDetailRoute
import com.haphap.app.presentation.register.navigation.navigateToRegisterFromJobDetail
import kotlinx.serialization.Serializable

fun NavController.navigateToJobDetail(
    postingId: Int,
    navOptions: NavOptions? = null,
) = navigate(JobDetail(postingId), navOptions)

fun NavGraphBuilder.jobDetailGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<JobDetail> { backStackEntry ->
        val route = backStackEntry.toRoute<JobDetail>()
        JobDetailRoute(
            navigateBack = { navController.popBackStack() },
            navigateToRegister = {
                navController.navigateToRegisterFromJobDetail(jobId = route.postingId.toLong())
            },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data class JobDetail(val postingId: Int) : Route
