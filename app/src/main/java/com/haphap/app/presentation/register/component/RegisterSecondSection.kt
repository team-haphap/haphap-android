package com.haphap.app.presentation.register.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.modal.HapHapDialog
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.presentation.register.type.PassResultStatusButton

@Composable
fun RegisterSecondSection(
    selectedResult: PassResultStatusButton?,
    onResultSelected: (PassResultStatusButton) -> Unit,
    isChangeModalVisible: Boolean,
    onChangeModalConfirmClick: () -> Unit,
    onChangeModalCancelClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(horizontal = 20.dp),
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "결과",
            style = HapHapTheme.typography.body.b18,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(9.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
        ) {
            PassResultStatusButton.entries.forEach { status ->
                RegisterResultButton(
                    status = status,
                    isSelected = status == selectedResult,
                    onClick = { onResultSelected(status) },
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }

    if (isChangeModalVisible) {
        HapHapDialog(
            content = "이전에 등록한 결과가 있습니다.\n결과를 변경할까요?",
            onDismiss = onChangeModalCancelClick,
            onConfirmClick = onChangeModalConfirmClick,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterSecondSectionPreview() {
    var selectedResult by remember { mutableStateOf<PassResultStatusButton?>(PassResultStatusButton.PASS) }

    HapHapTheme {
        RegisterSecondSection(
            selectedResult = selectedResult,
            onResultSelected = { selectedResult = it },
            isChangeModalVisible = false,
            onChangeModalConfirmClick = {},
            onChangeModalCancelClick = {},
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterSecondSectionChangeModalPreview() {
    HapHapTheme {
        RegisterSecondSection(
            selectedResult = PassResultStatusButton.FAILED,
            onResultSelected = {},
            isChangeModalVisible = true,
            onChangeModalConfirmClick = {},
            onChangeModalCancelClick = {},
        )
    }
}