package com.haphap.app.presentation.passcertification

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType
import com.haphap.app.core.extensions.toastBottomInset
import com.haphap.app.data.model.passcertification.PassCertificationDetailModel
import com.haphap.app.presentation.passcertification.component.PassCertificationImageSection
import com.haphap.app.presentation.passcertification.component.PassCertificationInfoSection
import com.haphap.app.presentation.passcertification.component.PassCertificationNotice
import com.haphap.app.presentation.passcertification.component.PassCertificationRejectReason
import com.haphap.app.presentation.passcertification.component.PassCertificationTopBar
import kotlinx.collections.immutable.persistentListOf

@Composable
fun PassCertificationDetailScreen(
    certification: PassCertificationDetailModel,
    onBackClick: () -> Unit,
    onSubmitClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        containerColor = HapHapTheme.colors.white,
        contentWindowInsets = WindowInsets(0),
        topBar = {
            PassCertificationTopBar(
                onBackClick = onBackClick,
                modifier = Modifier.padding(horizontal = 20.dp),
            )
        },
        bottomBar = {
            Column(modifier = Modifier.toastBottomInset()) {
                Spacer(modifier = Modifier.height(15.dp))

                PassCertificationNotice()

                HapHapBasicButton(
                    text = "인증자료 제출하기",
                    textStyle = HapHapTheme.typography.body.b18,
                    colorType = ButtonType.Primary(enabled = true),
                    onClick = onSubmitClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                )
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp),
        ) {
            Spacer(modifier = Modifier.height(36.dp))

            PassCertificationInfoSection(
                companyName = certification.companyName,
                title = certification.title,
                stageName = certification.stageName,
                resultText = certification.resultText,
                statusText = certification.statusText,
                submittedDate = certification.submittedDate,
            )

            Spacer(modifier = Modifier.height(24.dp))

            PassCertificationRejectReason(
                reason = certification.rejectReason,
            )

            Spacer(modifier = Modifier.height(24.dp))

            PassCertificationImageSection(
                imageUrls = certification.imageUrls,
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PassCertificationDetailScreenPreview() {
    HapHapTheme {
        PassCertificationDetailScreen(
            certification = PassCertificationDetailModel(
                companyName = "가온테크",
                title = "서비스 기획 인턴",
                stageName = "서류",
                resultText = "합격",
                statusText = "반려",
                submittedDate = "2026.10.15.",
                rejectReason = "첨부 이미지에서 공고명과 합격 여부를 확인하기 어려워요.",
                imageUrls = persistentListOf(""),
            ),
            onBackClick = {},
            onSubmitClick = {},
        )
    }
}
