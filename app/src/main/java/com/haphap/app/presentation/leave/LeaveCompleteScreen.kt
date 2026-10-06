package com.haphap.app.presentation.leave

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
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
            .padding(start = 20.dp, end = 20.dp, top = 36.dp),
    ) {
        Text(
            text = "회원 탈퇴가 완료됐어요.",
            style = HapHapTheme.typography.subtitle.b22,
            color = HapHapTheme.colors.gray800,
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "앞으로의 여정도 응원할게요.\n언제든 다시 만나요!",
            style = HapHapTheme.typography.body.m14,
            color = HapHapTheme.colors.gray500,
        )

        Spacer(modifier = Modifier.weight(132f / 342f))

        Image(
            painter = painterResource(R.drawable.img_register_check),
            contentDescription = null,
            modifier = Modifier
                .size(210.dp)
                .align(Alignment.CenterHorizontally),
        )

        Spacer(modifier = Modifier.weight(210f / 342f))

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
