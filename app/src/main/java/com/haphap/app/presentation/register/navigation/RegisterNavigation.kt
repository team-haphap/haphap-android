package com.haphap.app.presentation.register.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.MainTabRoute
import com.haphap.app.presentation.home.navigation.navigateToHome
import com.haphap.app.presentation.register.RegisterRoute
import kotlinx.serialization.Serializable

fun NavController.navigateToRegister(
    navOptions: NavOptions? = null,
) = navigate(Register(jobId = null), navOptions)

fun NavController.navigateToRegisterFromJobDetail(
    jobId: Long,
    navOptions: NavOptions? = null,
) = navigate(Register(jobId = jobId), navOptions)

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
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data class Register(val jobId: Long? = null) : MainTabRoute