package com.haphap.app.presentation.joblist.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.MainTabRoute
import com.haphap.app.presentation.joblist.JobListRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToJobList(
    navOptions: NavOptions? = null
) = navigate(JobList, navOptions)

fun NavGraphBuilder.jobListGraph(
    innerPadding: PaddingValues,
) {
    composable<JobList> {
        JobListRoute(
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object JobList: MainTabRoute
