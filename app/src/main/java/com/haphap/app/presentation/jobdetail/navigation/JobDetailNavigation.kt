package com.haphap.app.presentation.jobdetail.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.jobdetail.JobDetailRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToJobDetail(
    postingId: Long,
    navOptions: NavOptions? = null,
) = navigate(JobDetail(postingId), navOptions)

fun NavGraphBuilder.jobDetailGraph(
    innerPadding: PaddingValues,
) {
    composable<JobDetail> {
        JobDetailRoute(
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data class JobDetail(val postingId: Long) : Route
