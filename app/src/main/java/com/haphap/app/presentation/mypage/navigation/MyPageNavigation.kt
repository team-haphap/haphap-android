package com.haphap.app.presentation.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.calendar.navigation.navigateToCalendar
import com.haphap.app.presentation.jobdetail.navigation.navigateToJobDetail
import com.haphap.app.presentation.myapplication.navigation.navigateToMyApplication
import com.haphap.app.presentation.mypage.MyPageRoute
import com.haphap.app.presentation.mypage.profile.ProfileRoute
import com.haphap.app.presentation.passcard.navigation.navigateToPassCardStorage
import com.haphap.app.presentation.passcertification.navigation.navigateToPassCertification
import com.haphap.app.presentation.scrap.navigation.navigateToScrapList
import com.haphap.app.presentation.setting.navigation.navigateToSetting
import kotlinx.serialization.Serializable

fun NavController.navigateToMyPage(
    navOptions: NavOptions? = null,
) = navigate(MyPage, navOptions)

fun NavController.navigateToProfile(
    navOptions: NavOptions? = null,
) = navigate(Profile, navOptions)

fun NavGraphBuilder.myPageGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<MyPage> {
        MyPageRoute(
            navigateBack = navController::popBackStack,
            navigateToSetting = navController::navigateToSetting,
            navigateToCalendar = navController::navigateToCalendar,
            navigateToJobDetail = navController::navigateToJobDetail,
            navigateToProfile = navController::navigateToProfile,
            navigateToPassCertification = navController::navigateToPassCertification,
            navigateToPassCardStorage = navController::navigateToPassCardStorage,
            navigateToMyApplication = navController::navigateToMyApplication,
            navigateToScrapList = navController::navigateToScrapList,
            modifier = Modifier.padding(innerPadding),
        )
    }

    composable<Profile> {
        ProfileRoute(
            navigateBack = navController::popBackStack,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object MyPage: Route

@Serializable
data object Profile : Route
