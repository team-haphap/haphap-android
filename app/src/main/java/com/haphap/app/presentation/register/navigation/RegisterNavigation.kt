package com.haphap.app.presentation.register.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navOptions
import androidx.navigation.toRoute
import com.haphap.app.core.navigation.MainTabRoute
import com.haphap.app.data.model.register.RegisterPassCardModel
import com.haphap.app.presentation.home.navigation.navigateToHome
import com.haphap.app.presentation.register.passcard.RegisterPassCardRoute
import com.haphap.app.presentation.register.RegisterRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToRegister(
    navOptions: NavOptions? = null,
) = navigate(Register(jobId = null), navOptions)

fun NavController.navigateToRegisterFromJobDetail(
    jobId: Long,
    navOptions: NavOptions? = null,
) = navigate(Register(jobId = jobId), navOptions)

fun NavController.navigateToRegisterPassCard(
    passCard: RegisterPassCardModel,
    navOptions: NavOptions? = null,
) = navigate(
    RegisterPassCard(
        userName = passCard.userName,
        recruitName = passCard.recruitName,
        companyName = passCard.companyName,
        logoUrl = passCard.logoUrl,
        backgroundImageUrl = passCard.backgroundImageUrl,
    ),
    navOptions,
)

fun NavGraphBuilder.registerGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<Register> {
        RegisterRoute(
            navigateBack = {
                if (!navController.popBackStack()) {
                    navController.navigateToHome(
                        navOptions = navOptions {
                            popUpTo<Register> { inclusive = true }
                            launchSingleTop = true
                        }
                    )
                }
            },
            navigateToHome = {
                navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo<Register> { inclusive = true }
                        launchSingleTop = true
                    }
                )
            },
            navigateToJobDetail = { jobId ->
                // TODO: 상세 페이지 네비게이션 함수 추가 예정
            },
            navigateToPassCard = { passCard ->
                navController.navigateToRegisterPassCard(
                    passCard = passCard
                )
            },
            modifier = Modifier.padding(innerPadding),
        )
    }

    composable<RegisterPassCard> { backStackEntry ->
        val route = backStackEntry.toRoute<RegisterPassCard>()
        RegisterPassCardRoute(
            passCardModel = RegisterPassCardModel(
                userName = route.userName,
                recruitName = route.recruitName,
                companyName = route.companyName,
                logoUrl = route.logoUrl,
                backgroundImageUrl = route.backgroundImageUrl,
            ),
            navigateToHome = {
                navController.navigateToHome(
                    navOptions = navOptions {
                        popUpTo<Register> { inclusive = true }
                        launchSingleTop = true
                    }
                )
            },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data class Register(val jobId: Long? = null) : MainTabRoute

@Serializable
data class RegisterPassCard(
    val userName: String,
    val recruitName: String,
    val companyName: String,
    val logoUrl: String,
    val backgroundImageUrl: String
)