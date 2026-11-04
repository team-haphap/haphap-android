package com.haphap.app.presentation.mypage.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.mypage.MyPageRoute
import com.haphap.app.presentation.setting.navigation.navigateToSetting
import kotlinx.serialization.Serializable

fun NavController.navigateToMyPage(
    navOptions: NavOptions? = null,
) = navigate(MyPage, navOptions)

fun NavGraphBuilder.myPageGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<MyPage> {
        MyPageRoute(
            navigateBack = navController::popBackStack,
            navigateToSetting = navController::navigateToSetting,
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object MyPage: Route
