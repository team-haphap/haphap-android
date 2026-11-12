package com.haphap.app.presentation.passcertification

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LifecycleEventEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.passcertification.PassCertificationModel
import com.haphap.app.presentation.passcertification.component.PassCertificationDescription
import com.haphap.app.presentation.passcertification.component.PassCertificationItem
import com.haphap.app.presentation.passcertification.component.PassCertificationTopBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PassCertificationRoute(
    navigateBack: () -> Unit,
    navigateToDetail: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: PassCertificationViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // 재제출 후 돌아왔을 때 목록에서 빠지도록 화면에 다시 들어올 때마다 조회
    LifecycleEventEffect(Lifecycle.Event.ON_RESUME) {
        viewModel.getRejectedCertifications()
    }

    PassCertificationScreen(
        certificationList = uiState.certificationList,
        onBackClick = navigateBack,
        onItemClick = navigateToDetail,
        modifier = modifier,
    )
}

@Composable
fun PassCertificationScreen(
    certificationList: ImmutableList<PassCertificationModel>,
    onBackClick: () -> Unit,
    onItemClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = HapHapTheme.colors.white)
            .padding(horizontal = 20.dp),
    ) {
        PassCertificationTopBar(
            onBackClick = onBackClick,
        )

        Spacer(modifier = Modifier.height(10.dp))

        PassCertificationDescription()

        LazyColumn(
            contentPadding = PaddingValues(vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            items(
                items = certificationList,
                key = { it.id },
            ) { certification ->
                PassCertificationItem(
                    imageUrl = certification.imageUrl,
                    companyName = certification.companyName,
                    stageName = certification.stageName,
                    title = certification.title,
                    onItemClick = { onItemClick(certification.id) },
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PassCertificationScreenPreview() {
    HapHapTheme {
        PassCertificationScreen(
            certificationList = persistentListOf(
                PassCertificationModel(
                    id = 1,
                    imageUrl = "",
                    companyName = "가온테크",
                    stageName = "서류",
                    title = "서비스 기획 인턴",
                ),
                PassCertificationModel(
                    id = 2,
                    imageUrl = "",
                    companyName = "카카오",
                    stageName = "과제",
                    title = "AI 서비스 기획",
                ),
                PassCertificationModel(
                    id = 3,
                    imageUrl = "",
                    companyName = "카카오",
                    stageName = "1차면접",
                    title = "AI 서비스 기획",
                ),
            ),
            onBackClick = {},
            onItemClick = {},
        )
    }
}
