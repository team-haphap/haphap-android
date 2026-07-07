package com.haphap.app.presentation.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.auth.navigation.Login
import com.haphap.app.presentation.calendar.navigation.navigateToCalendar
import com.haphap.app.presentation.home.navigation.navigateToHome
import com.haphap.app.presentation.job.navigation.navigateToJob
import com.haphap.app.presentation.main.component.MainTab
import com.haphap.app.presentation.mypage.navigation.navigateToMyPage
import com.haphap.app.presentation.register.navigation.navigateToRegister
import com.haphap.app.presentation.splash.navigation.Splash
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Stable
class MainAppState(
    val navController: NavHostController,
    coroutineScope: CoroutineScope,
) {
    val startDestination: Route = Splash

    private val currentDestination = navController.currentBackStackEntryFlow
        .map { it.destination }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val currentTab: StateFlow<MainTab?> = currentDestination
        .map { destination ->
            MainTab.find { tab ->
                destination?.hasRoute(tab::class) == true
            }
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )

    val isBottomBarVisible: StateFlow<Boolean> = currentDestination
        .map { destination ->
            MainTab.contains { tab ->
                destination?.hasRoute(tab::class) == true
            }
        }
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = false
        )

    fun navigate(tab: MainTab) {
        val navOptions = navOptions {
            navController.currentDestination?.route?.let {
                popUpTo(it) {
                    inclusive = true
                }
                launchSingleTop = true
            }
        }

        when (tab) {
            MainTab.HOME -> navController.navigateToHome(navOptions = navOptions)
            MainTab.JOB -> navController.navigateToJob(navOptions = navOptions)
            MainTab.REGISTER -> navController.navigateToRegister(navOptions = navOptions)
            MainTab.CALENDAR -> navController.navigateToCalendar(navOptions = navOptions)
            MainTab.MYPAGE -> navController.navigateToMyPage(navOptions = navOptions)
        }
    }

}

@Composable
fun rememberMainAppState(
    navController: NavHostController = rememberNavController(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): MainAppState = remember(navController, coroutineScope) {
    MainAppState(navController, coroutineScope)
}
