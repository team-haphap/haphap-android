package com.haphap.app.presentation.register.component

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.toDotDateFormat
import com.haphap.app.core.extensions.toKoreanTimeFormat
import com.haphap.app.presentation.register.type.PassResultStatusType
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@Composable
fun RegisterConfirmSection(
    recruitName: String,
    recruitProcess: String,
    contactDate: String?,
    contactTime: String?,
    selectedResult: PassResultStatusType,
    isAlarmAgreed: Boolean,
    onAlarmAgreeToggled: (Boolean) -> Unit,
    isTermAgreed: Boolean,
    onTermAgreeToggled: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    proofImages: ImmutableList<Uri> = persistentListOf()
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(48.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState()),
        ) {
            Text(
                text = "입력한 정보를 확인해 주세요",
                style = HapHapTheme.typography.body.b18,
                color = HapHapTheme.colors.gray800,
            )

            Spacer(modifier = Modifier.height(15.dp))

            RegisterResultConfirm(
                recruitName = recruitName,
                recruitProcess = recruitProcess,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(36.dp))

            Text(
                text = if (selectedResult == PassResultStatusType.DONT_KNOW) "결과" else "날짜 및 결과",
                style = HapHapTheme.typography.body.b18,
                color = HapHapTheme.colors.gray800,
            )

            Spacer(modifier = Modifier.height(15.dp))

            if (contactDate != null && contactTime != null) {
                Row {
                    ConfirmInfoBox(
                        text = contactDate.toDotDateFormat(),
                        modifier = Modifier.weight(1f),
                    )

                    Spacer(modifier = Modifier.width(12.dp))

                    ConfirmInfoBox(
                        text = contactTime.toKoreanTimeFormat(),
                        modifier = Modifier.weight(1f),
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))
            }

            RegisterPassResultConfirm(
                status = selectedResult,
                modifier = Modifier.fillMaxWidth()
            )

            if (selectedResult == PassResultStatusType.PASS && proofImages.isNotEmpty()) {
                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "합격 인증",
                    style = HapHapTheme.typography.body.b18,
                    color = HapHapTheme.colors.gray800,
                )

                Spacer(modifier = Modifier.height(15.dp))

                RegisterUploadedImageRow(images = proofImages)
            }
        }

        RegisterTnC(
            isNecessary = true,
            context = "등록이 완료되면 결과를 수정하거나 삭제할 수 없어요.\n변경이 필요한 경우 [문의하기]를 이용해주세요.",
            checked = isTermAgreed,
            onCheckedChange = onTermAgreeToggled,
        )

        RegisterTnC(
            isNecessary = false,
            context = "선택한 공고에 대한 알림을 받고 싶어요.",
            checked = isAlarmAgreed,
            onCheckedChange = onAlarmAgreeToggled,
        )

        Spacer(modifier = Modifier.height(12.dp))
    }
}

@Composable
private fun ConfirmInfoBox(
    text: String,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .background(
                color = HapHapTheme.colors.gray100,
                shape = RoundedCornerShape(8.dp),
            )
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center,
    ) {
        Text(
            text = text,
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray600,
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterConfirmSectionPreview() {
    var isAlarmAgreed by remember { mutableStateOf(true) }
    var isTermAgreed by remember { mutableStateOf(true) }

    HapHapTheme {
        RegisterConfirmSection(
            recruitName = "카카오 2026 신입 개발자 공개 채용",
            recruitProcess = "코딩테스트",
            contactDate = "2026-06-11",
            contactTime = "21:33",
            selectedResult = PassResultStatusType.PASS,
            isAlarmAgreed = isAlarmAgreed,
            onAlarmAgreeToggled = { isAlarmAgreed = it },
            isTermAgreed = isTermAgreed,
            onTermAgreeToggled = { isTermAgreed = it },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF, widthDp = 360, heightDp = 640)
@Composable
private fun RegisterConfirmSectionPassProofPreview() {
    HapHapTheme {
        RegisterConfirmSection(
            recruitName = "카카오 2026 신입 개발자 공개 채용",
            recruitProcess = "코딩테스트",
            contactDate = "2026-06-11",
            contactTime = "21:33",
            selectedResult = PassResultStatusType.PASS,
            isAlarmAgreed = false,
            onAlarmAgreeToggled = {},
            isTermAgreed = true,
            onTermAgreeToggled = {},
            modifier = Modifier.fillMaxSize(),
            proofImages = persistentListOf(Uri.parse("content://preview/1")),
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterConfirmSectionDontKnowPreview() {
    var isAlarmAgreed by remember { mutableStateOf(false) }
    var isTermAgreed by remember { mutableStateOf(false) }

    HapHapTheme {
        RegisterConfirmSection(
            recruitName = "카카오 2026 신입 개발자 공개 채용",
            recruitProcess = "코딩테스트",
            contactDate = null,
            contactTime = null,
            selectedResult = PassResultStatusType.DONT_KNOW,
            isAlarmAgreed = isAlarmAgreed,
            onAlarmAgreeToggled = { isAlarmAgreed = it },
            isTermAgreed = isTermAgreed,
            onTermAgreeToggled = { isTermAgreed = it },
        )
    }
}
