package com.haphap.app.presentation.calendar.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.MainTabRoute
import com.haphap.app.presentation.calendar.CalendarRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToCalendar(
    navOptions: NavOptions? = null,
) = navigate(Calendar, navOptions)

fun NavGraphBuilder.calendarGraph(
    innerPadding: PaddingValues,
) {
    composable<Calendar> {
        CalendarRoute(
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object Calendar: MainTabRoute
