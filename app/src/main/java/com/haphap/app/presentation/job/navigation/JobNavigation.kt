package com.haphap.app.presentation.job.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.MainTabRoute
import com.haphap.app.presentation.job.JobRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToJob(
    navOptions: NavOptions? = null
) = navigate(Job, navOptions)

fun NavGraphBuilder.jobGraph(
    innerPadding: PaddingValues,
) {
    composable<Job> {
        JobRoute(
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Job: MainTabRoute
