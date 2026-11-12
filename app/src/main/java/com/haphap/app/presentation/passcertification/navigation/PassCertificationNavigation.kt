package com.haphap.app.presentation.passcertification.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.dropUnlessResumed
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.haphap.app.core.navigation.Route
import com.haphap.app.data.model.passcertification.PassCertificationDetailModel
import com.haphap.app.presentation.passcertification.PassCertificationDetailScreen
import com.haphap.app.presentation.passcertification.PassCertificationScreen
import kotlinx.collections.immutable.persistentListOf
import kotlinx.serialization.Serializable

fun NavController.navigateToPassCertification(
    navOptions: NavOptions? = null,
) = navigate(PassCertification, navOptions)

fun NavController.navigateToPassCertificationDetail(
    certificationId: Long,
    navOptions: NavOptions? = null,
) = navigate(PassCertificationDetail(certificationId), navOptions)

fun NavGraphBuilder.passCertificationGraph(
    innerPadding: PaddingValues,
    navController: NavController,
) {
    composable<PassCertification> {
        // TODO: ViewModel 연결 시 반려 목록 데이터로 교체
        PassCertificationScreen(
            certificationList = persistentListOf(),
            onBackClick = dropUnlessResumed { navController.popBackStack() },
            onItemClick = { certificationId ->
                navController.navigateToPassCertificationDetail(certificationId = certificationId)
            },
            modifier = Modifier.padding(innerPadding),
        )
    }

    composable<PassCertificationDetail> {
        // TODO: ViewModel 연결 시 상세 데이터, 재제출 모드, 이미지 추가/제출 처리로 교체
        var isResubmitMode by rememberSaveable { mutableStateOf(false) }

        PassCertificationDetailScreen(
            certification = PassCertificationDetailModel(),
            isResubmitMode = isResubmitMode,
            newImageUrls = persistentListOf(),
            onBackClick = dropUnlessResumed { navController.popBackStack() },
            onSubmitClick = { isResubmitMode = true },
            onAddImageClick = {},
            onDeleteImageClick = {},
            onResubmitClick = {},
            modifier = Modifier.padding(innerPadding),
        )
    }
}

@Serializable
data object PassCertification : Route

@Serializable
data class PassCertificationDetail(val certificationId: Long) : Route
