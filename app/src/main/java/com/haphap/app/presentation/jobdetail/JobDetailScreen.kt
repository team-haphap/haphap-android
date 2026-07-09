package com.haphap.app.presentation.jobdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.haphap.app.R
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.component.button.HapHapRefreshButton
import com.haphap.app.core.designsystem.component.image.UrlImage
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType
import com.haphap.app.data.model.detail.JobParticipantModel
import com.haphap.app.data.model.detail.JobResultModel
import com.haphap.app.data.model.detail.JobStepModel
import com.haphap.app.data.model.detail.JobStepReportModel
import com.haphap.app.data.model.detail.JobTitleModel
import com.haphap.app.presentation.jobdetail.component.JobDetailTitleSection
import com.haphap.app.presentation.jobdetail.component.JobDetailTopBar
import com.haphap.app.presentation.jobdetail.component.JobParticipantSection
import com.haphap.app.presentation.jobdetail.component.JobResultCard
import com.haphap.app.presentation.jobdetail.component.JobResultTabRow
import com.haphap.app.presentation.jobdetail.component.JobStageStepRow
import com.haphap.app.presentation.jobdetail.component.JobStepReportItem
import com.haphap.app.presentation.jobdetail.type.JobResultCardType
import com.haphap.app.presentation.jobdetail.type.JobStepReportType
import com.haphap.app.presentation.jobdetail.type.JobStepStatus
import kotlinx.collections.immutable.persistentListOf

@Composable
fun JobDetailRoute(
    modifier: Modifier = Modifier,
    viewModel: JobDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    JobDetailScreen(
        uiState = uiState,
        onBackClick = {},
        onAlarmClick = {},
        onMoreClick = {},
        onTabClick = viewModel::updateSelectedTab,
        onRefreshClick = {},
        onRegisterClick = {},
        modifier = modifier,
    )
}

@Composable
private fun JobDetailScreen(
    uiState: JobDetailContract.State,
    onBackClick: () -> Unit,
    onAlarmClick: () -> Unit,
    onMoreClick: () -> Unit,
    onTabClick: (Int) -> Unit,
    onRefreshClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),

        topBar = {
            JobDetailTopBar(
                onBackClick = onBackClick,
                onAlarmClick = onAlarmClick,
                onMoreClick = onMoreClick,
                isAlarmActive = uiState.isAlarmActive,
            )
        },

        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HapHapTheme.colors.white)
                    .padding(horizontal = 20.dp, vertical = 10.dp),
            ) {
                HapHapBasicButton(
                    text = "등록하기",
                    textStyle = HapHapTheme.typography.body.b18,
                    colorType = ButtonType.Primary(enabled = true),
                    onClick = onRegisterClick,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(HapHapTheme.colors.white)
                .padding(innerPadding)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                item { Spacer(modifier = Modifier.height(12.dp)) }

                item {
                    JobDetailTitleSection(
                        companyName = uiState.titleInfo.companyName,
                        jobTitle = uiState.titleInfo.jobTitle,
                        keywords = uiState.titleInfo.keywords,
                        statusText = uiState.titleInfo.currentState,
                    )
                }

                item { Spacer(modifier = Modifier.height(12.dp)) }

                item {
                    UrlImage(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(360f / 190f),
                        url = uiState.bannerImageUrl,
                        placeholderDrawable = R.drawable.ic_launcher_background,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                    )
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }

                item {
                    Column {
                        SectionTitle(title = "전형 단계")
                        Spacer(modifier = Modifier.height(9.dp))
                        JobStageStepRow(steps = uiState.stages)
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }

                item {
                    Column {
                        SectionTitle(title = "전형별 등록 결과")
                        Spacer(modifier = Modifier.height(12.dp))

                        JobResultTabRow(
                            stages = uiState.resultTabs,
                            selectedStage = uiState.selectedTab,
                            onStageClick = onTabClick,
                        )

                        Spacer(modifier = Modifier.height(13.dp))

                        Row(
                            modifier = Modifier.padding(horizontal = 20.dp),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                        ) {
                            JobResultCard(
                                type = JobResultCardType.PASS,
                                count = uiState.result.passCount,
                                modifier = Modifier.weight(1f),
                            )
                            JobResultCard(
                                type = JobResultCardType.FAIL,
                                count = uiState.result.failCount,
                                modifier = Modifier.weight(1f),
                            )
                            JobResultCard(
                                type = JobResultCardType.PENDING,
                                count = uiState.result.pendingCount,
                                modifier = Modifier.weight(1f),
                            )
                        }

                        Spacer(modifier = Modifier.height(36.dp))
                    }
                }

                item {
                    JobParticipantSection(
                        registeredCount = uiState.participant.registeredCount,
                        profileImages = uiState.participant.profileImages,
                        additionalCount = uiState.participant.additionalParticipantCount,
                    )
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }

                item {
                    HorizontalDivider(
                        thickness = 2.dp,
                        color = HapHapTheme.colors.gray100
                    )
                }

                item { Spacer(modifier = Modifier.height(24.dp)) }

                item {
                    SectionTitle(title = "실시간 전형 제보")
                }

                item { Spacer(modifier = Modifier.height(12.dp)) }

                items(
                    items = uiState.reports,
                    key = { it.id },
                ) { report ->
                    JobStepReportItem(
                        time = report.time,
                        nickName = report.nickName,
                        result = report.result,
                        stage = report.stage,
                    )
                }

                item { Spacer(modifier = Modifier.height(12.dp)) }
            }
            HapHapRefreshButton(
                onButtonClick = onRefreshClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(bottom = 10.dp, end = 20.dp),
            )
        }
    }
}

@Composable
private fun SectionTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Text(
        text = title,
        style = HapHapTheme.typography.body.b18,
        color = HapHapTheme.colors.gray800,
        modifier = modifier.padding(horizontal = 20.dp),
    )
}

@Preview(showBackground = true)
@Composable
private fun JobDetailScreenPreview() {
    HapHapTheme {
        var selectedTab by remember { mutableStateOf(0) }

        JobDetailScreen(
            uiState = JobDetailContract.State(
                titleInfo = JobTitleModel(
                    companyName = "카카오 에너지",
                    jobTitle = "태양광 사업 정책기획 태양광 사업 정책기획 태양광 사업 정책기획 태양광 사업 정책기획 태양광 사업 정책기획",
                    keywords = persistentListOf("사업/기획", "양재 본사", "UXUI"),
                    currentState = "1차 면접 진행 중",
                ),
                stages = persistentListOf(
                    JobStepModel(1, 1, "서류", JobStepStatus.COMPLETED),
                    JobStepModel(2, 2, "서류", JobStepStatus.COMPLETED),
                    JobStepModel(3, 3, "1차면접", JobStepStatus.IN_PROGRESS),
                ),
                resultTabs = persistentListOf("서류", "인적성", "코딩테스트", "1차면접", "2차면접"),
                selectedTab = selectedTab,
                result = JobResultModel(
                    passCount = 12,
                    failCount = 12,
                    pendingCount = 12,
                ),
                participant = JobParticipantModel(
                    registeredCount = 132,
                    profileImages = persistentListOf("", "", "", ""),
                    additionalParticipantCount = 129,
                ),
                reports = persistentListOf(
                    JobStepReportModel(1L, "12:21", "익명의 라이언", JobStepReportType.PASS, "서류"),
                    JobStepReportModel(2L, "12:21", "익명의 라이언", JobStepReportType.FAIL, "서류"),
                    JobStepReportModel(3L, "12:21", "익명의 라이언", JobStepReportType.PENDING, "서류"),
                    JobStepReportModel(4L, "12:21", "익명의 라이언", JobStepReportType.PENDING, "서류"),
                    JobStepReportModel(5L, "12:21", "익명의 라이언", JobStepReportType.PENDING, "서류"),
                ),
            ),
            onBackClick = {},
            onAlarmClick = {},
            onMoreClick = {},
            onTabClick = { selectedTab = it },
            onRefreshClick = {},
            onRegisterClick = {},
        )
    }
}