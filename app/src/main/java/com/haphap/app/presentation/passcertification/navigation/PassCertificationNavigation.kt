package com.haphap.app.presentation.passcertification.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import com.haphap.app.core.navigation.Route
import com.haphap.app.presentation.mypage.navigation.MyPage
import com.haphap.app.presentation.passcertification.PassCertificationRoute
import com.haphap.app.presentation.passcertification.complete.PassCertificationCompleteRoute
import com.haphap.app.presentation.passcertification.detail.PassCertificationDetailRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToPassCertification(
    navOptions: NavOptions? = null,
) = navigate(PassCertification, navOptions)

fun NavController.navigateToPassCertificationDetail(
    certificationId: Long,
    navOptions: NavOptions? = null,
) = navigate(PassCertificationDetail(certificationId), navOptions)

fun NavController.navigateToPassCertificationComplete(
    navOptions: NavOptions? = null,
) = navigate(PassCertificationComplete, navOptions)

fun NavGraphBuilder.passCertificationGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<PassCertification> {
        PassCertificationRoute(
            navigateBack = dropUnlessResumed { navController.popBackStack() },
            navigateToDetail = navController::navigateToPassCertificationDetail,
            modifier = Modifier.padding(innerPadding),
        )
    }

    composable<PassCertificationDetail> {
        PassCertificationDetailRoute(
            navigateBack = dropUnlessResumed { navController.popBackStack() },
            navigateToComplete = {
                navController.navigateToPassCertificationComplete(
                    navOptions = navOptions {
                        popUpTo<PassCertificationDetail> {
                            inclusive = true
                        }
                    },
                )
            },
            modifier = Modifier.padding(innerPadding),
        )
    }

    composable<PassCertificationComplete> {
        PassCertificationCompleteRoute(
            navigateToList = dropUnlessResumed {
                navController.popBackStack<PassCertification>(inclusive = false)
            },
            navigateToMyPage = dropUnlessResumed {
                navController.popBackStack<MyPage>(inclusive = false)
            },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object PassCertification : Route

@Serializable
data class PassCertificationDetail(val certificationId: Long) : Route

@Serializable
data object PassCertificationComplete : Route
