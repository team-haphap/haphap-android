package com.haphap.app.presentation.register.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.haphap.app.core.navigation.MainTabRoute
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
    recruitName: String,
    companyName: String,
    logoUrl: String,
    backgroundImageUrl: String,
    navOptions: NavOptions? = null,
) = navigate(
    RegisterPassCard(
        recruitName = recruitName,
        companyName = companyName,
        logoUrl = logoUrl,
        backgroundImageUrl = backgroundImageUrl,
    ),
    navOptions,
)

fun NavGraphBuilder.registerGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<Register> {
        RegisterRoute(
            navigateBack = { navController.popBackStack() },
            navigateToHome = { navController.navigateToHome() },
            navigateToJobDetail = { jobId ->
                // TODO: 상세 페이지 네비게이션 함수 추가 예정
            },
            navigateToPassCard = { recruitName, companyName, logoUrl, backgroundImageUrl ->
                navController.navigateToRegisterPassCard(
                    recruitName = recruitName,
                    companyName = companyName,
                    logoUrl = logoUrl,
                    backgroundImageUrl = backgroundImageUrl,
                )
            },
            modifier = Modifier.padding(innerPadding),
        )
    }

    composable<RegisterPassCard> { backStackEntry ->
        val route = backStackEntry.toRoute<RegisterPassCard>()
        RegisterPassCardRoute(
            recruitName = route.recruitName,
            companyName = route.companyName,
            logoUrl = route.logoUrl,
            backgroundImageUrl = route.backgroundImageUrl,
            navigateToHome = { navController.navigateToHome() },
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data class Register(val jobId: Long? = null) : MainTabRoute

@Serializable
data class RegisterPassCard(
    val recruitName: String,
    val companyName: String,
    val logoUrl: String,
    val backgroundImageUrl: String
)