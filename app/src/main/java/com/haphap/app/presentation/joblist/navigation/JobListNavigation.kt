package com.haphap.app.presentation.joblist.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.MainTabRoute
import com.haphap.app.presentation.jobdetail.navigation.navigateToJobDetail
import com.haphap.app.presentation.joblist.JobListRoute
import com.haphap.app.presentation.search.navigation.navigateToSearch
import kotlinx.serialization.Serializable

fun NavController.navigateToJobList(
    navOptions: NavOptions? = null
) = navigate(JobList, navOptions)

fun NavGraphBuilder.jobListGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<JobList> {
        JobListRoute(
            navigateToSearch = navController::navigateToSearch,
            navigateToJobDetail = navController::navigateToJobDetail,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object JobList: MainTabRoute
