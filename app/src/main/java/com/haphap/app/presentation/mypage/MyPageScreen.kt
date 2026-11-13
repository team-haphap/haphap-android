package com.haphap.app.presentation.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.data.model.mypage.MyRecentApplicationModel
import com.haphap.app.presentation.mypage.component.MyPageAnnouncementBanner
import com.haphap.app.presentation.mypage.component.MyPageApplicationSection
import com.haphap.app.presentation.mypage.component.MyPageMenuItem
import com.haphap.app.presentation.mypage.component.MyPagePassCertificationCard
import com.haphap.app.presentation.mypage.component.MyPageProfileSection
import com.haphap.app.presentation.mypage.component.MyPageTopBar
import com.haphap.app.presentation.mypage.type.PassCertificationType
import kotlinx.collections.immutable.persistentListOf

@Composable
fun MyPageRoute(
    navigateBack: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToCalendar: () -> Unit,
    navigateToJobDetail: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: MyPageViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    // TODO: 클릭 이벤트는 추후 연결
    MyPageScreen(
        uiState = uiState,
        onBackClick = navigateBack,
        onSettingClick = navigateToSetting,
        onAlarmClick = {},
        onProfileEditClick = {},
        onPointClick = {},
        onAnnouncementClick = navigateToCalendar,
        onSubmitCertificationClick = {},
        onPassCardClick = {},
        onApplicationClick = navigateToJobDetail,
        onApplicationMoreClick = {},
        onBookmarkedJobClick = {},
        onPassCardStorageClick = {},
        onMyContentClick = {},
        modifier = modifier,
    )
}

@Composable
private fun MyPageScreen(
    uiState: MyPageContract.State,
    onBackClick: () -> Unit,
    onSettingClick: () -> Unit,
    onAlarmClick: () -> Unit,
    onProfileEditClick: () -> Unit,
    onPointClick: () -> Unit,
    onAnnouncementClick: () -> Unit,
    onSubmitCertificationClick: () -> Unit,
    onPassCardClick: () -> Unit,
    onApplicationClick: (Int) -> Unit,
    onApplicationMoreClick: () -> Unit,
    onBookmarkedJobClick: () -> Unit,
    onPassCardStorageClick: () -> Unit,
    onMyContentClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HapHapTheme.colors.white),
    ) {
        MyPageTopBar(
            onBackClick = onBackClick,
            onSettingClick = onSettingClick,
            alarmCount = uiState.alarmCount,
            onAlarmClick = onAlarmClick,
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(bottom = 50.dp),
        ) {
            MyPageProfileSection(
                profileImageUrl = uiState.profileUrl,
                nickName = uiState.nickNameText,
                point = uiState.point,
                onProfileEditClick = onProfileEditClick,
                onPointClick = onPointClick,
            )

            if (uiState.hasUpcomingAnnouncement) {
                MyPageAnnouncementBanner(
                    onBannerClick = onAnnouncementClick,
                )

                Spacer(modifier = Modifier.height(12.dp))
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Min)
                    .padding(horizontal = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                MyPagePassCertificationCard(
                    type = PassCertificationType.REJECTED,
                    count = uiState.rejectedCertificationCount,
                    onButtonClick = onSubmitCertificationClick,
                    modifier = Modifier.weight(1f),
                )

                MyPagePassCertificationCard(
                    type = PassCertificationType.APPROVED,
                    count = uiState.approvedCertificationCount,
                    onButtonClick = onPassCardClick,
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            MyPageApplicationSection(
                applicationList = uiState.applicationList,
                onApplicationClick = onApplicationClick,
                onMoreClick = onApplicationMoreClick,
            )

            Spacer(modifier = Modifier.height(16.dp))

            MyPageMenuItem(
                text = "내가 찜한 공고",
                onClick = onBookmarkedJobClick,
            )

            MyPageMenuItem(
                text = "합격카드 보관함",
                onClick = onPassCardStorageClick,
            )

            MyPageMenuItem(
                text = "내가 작성한 콘텐츠",
                onClick = onMyContentClick,
            )
        }
    }
}

@Preview(showBackground = true, heightDp = 900)
@Composable
private fun MyPageScreenPreview() {
    HapHapTheme {
        MyPageScreen(
            uiState = MyPageContract.State(
                nickNameText = "익명의 조르디",
                point = 300,
                alarmCount = 3,
                rejectedCertificationCount = 0,
                approvedCertificationCount = 2,
                hasUpcomingAnnouncement = true,
                applicationList = persistentListOf(
                    MyRecentApplicationModel(
                        id = 1,
                        title = "2026 신입 개발자 공개채용",
                        stageName = "서류 발표",
                        participantCount = 12,
                        appliedDate = "2024.02.09",
                        logoImageUrl = "",
                    ),
                    MyRecentApplicationModel(
                        id = 2,
                        title = "2026 상반기 신입 공채",
                        stageName = "서류 발표",
                        participantCount = 8,
                        appliedDate = "2024.02.10",
                        logoImageUrl = "",
                    ),
                ),
            ),
            onBackClick = {},
            onSettingClick = {},
            onAlarmClick = {},
            onProfileEditClick = {},
            onPointClick = {},
            onAnnouncementClick = {},
            onSubmitCertificationClick = {},
            onPassCardClick = {},
            onApplicationClick = {},
            onApplicationMoreClick = {},
            onBookmarkedJobClick = {},
            onPassCardStorageClick = {},
            onMyContentClick = {},
        )
    }
}
