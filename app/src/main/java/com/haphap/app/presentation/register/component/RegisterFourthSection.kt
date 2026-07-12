package com.haphap.app.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.haphap.app.presentation.register.type.PassResultStatusButton
import com.haphap.app.presentation.register.type.toPassResultType
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun RegisterFourthSection(
    recruitName: String,
    recruitProcess: String,
    contactDate: String?,
    contactTime: String?,
    selectedResult: PassResultStatusButton,
    isAlarmAgreed: Boolean,
    onAlarmAgreeToggled: (Boolean) -> Unit,
    isTermAgreed: Boolean,
    onTermAgreeToggled: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    val dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")
    val timeFormatter = DateTimeFormatter.ofPattern("HH시 mm분")

    Column(
        modifier = modifier.padding(horizontal = 20.dp),
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
            text = if (selectedResult == PassResultStatusButton.DONT_KNOW) "결과" else "날짜 및 결과",
            style = HapHapTheme.typography.body.b18,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(15.dp))

        if (contactDate != null && contactTime != null) {
            Row {
                ConfirmInfoBox(
                    text = LocalDate.parse(contactDate).format(dateFormatter),
                    modifier = Modifier.weight(1f),
                )

                Spacer(modifier = Modifier.width(12.dp))

                ConfirmInfoBox(
                    text = LocalTime.parse(contactTime).format(timeFormatter),
                    modifier = Modifier.weight(1f),
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }

        RegisterPassResultConfirm(
            status = selectedResult.toPassResultType(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.weight(1f))

        RegisterTnC(
            isNecessary = false,
            context = "같은 공고의 발표가 감지되면 알림을 받습니다.",
            checked = isAlarmAgreed,
            onCheckedChange = onAlarmAgreeToggled,
        )

        RegisterTnC(
            isNecessary = true,
            context = "내 상태는 부여된 닉네임으로 익명 등록되며, 중복·허위 등록 방지를 위해 기기 단위의 최소 검증이 적용됩니다.",
            checked = isTermAgreed,
            onCheckedChange = onTermAgreeToggled,
        )

        Spacer(modifier = Modifier.height(30.dp))
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
private fun RegisterFourthSectionPreview() {
    var isAlarmAgreed by remember { mutableStateOf(true) }
    var isTermAgreed by remember { mutableStateOf(true) }

    HapHapTheme {
        RegisterFourthSection(
            recruitName = "카카오 2026 신입 개발자 공개 채용",
            recruitProcess = "코딩테스트",
            contactDate = "2026-06-11",
            contactTime = "21:33",
            selectedResult = PassResultStatusButton.PASS,
            isAlarmAgreed = isAlarmAgreed,
            onAlarmAgreeToggled = { isAlarmAgreed = it },
            isTermAgreed = isTermAgreed,
            onTermAgreeToggled = { isTermAgreed = it },
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterFourthSectionDontKnowPreview() {
    var isAlarmAgreed by remember { mutableStateOf(false) }
    var isTermAgreed by remember { mutableStateOf(false) }

    HapHapTheme {
        RegisterFourthSection(
            recruitName = "카카오 2026 신입 개발자 공개 채용",
            recruitProcess = "코딩테스트",
            contactDate = null,
            contactTime = null,
            selectedResult = PassResultStatusButton.DONT_KNOW,
            isAlarmAgreed = isAlarmAgreed,
            onAlarmAgreeToggled = { isAlarmAgreed = it },
            isTermAgreed = isTermAgreed,
            onTermAgreeToggled = { isTermAgreed = it },
        )
    }
}