package com.haphap.app.presentation.leave

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.component.button.HapHapBasicButton
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.designsystem.type.ButtonType

@Composable
fun LeaveCompleteRoute(
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LeaveCompleteScreen(
        navigateToLogin = navigateToLogin,
        modifier = modifier,
    )
}

@Composable
fun LeaveCompleteScreen(
    navigateToLogin: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HapHapTheme.colors.white)
            .padding(start = 20.dp, end = 20.dp, top = 43.dp),
    ) {
        Text(
            text = "회원 탈퇴가 완료되었어요.",
            style = HapHapTheme.typography.subtitle.b22,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.weight(1f))

        HapHapBasicButton(
            text = "완료",
            textStyle = HapHapTheme.typography.body.b18,
            colorType = ButtonType.Primary(enabled = true),
            onClick = navigateToLogin,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LeaveCompleteScreenPreview() {
    HapHapTheme {
        LeaveCompleteScreen(
            navigateToLogin = {},
        )
    }
}
