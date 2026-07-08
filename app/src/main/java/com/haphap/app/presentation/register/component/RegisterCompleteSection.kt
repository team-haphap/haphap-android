package com.haphap.app.presentation.register.component

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
fun RegisterCompleteSection(
    onBackClick: () -> Unit,
    onCompleteClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(HapHapTheme.colors.white)
            .padding(bottom = 10.dp),
    ) {
        RegisterTopBar(
            onBackClick = onBackClick,
            isText = false,
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
        ) {
            Text(
                text = "등록이 완료되었어요!",
                style = HapHapTheme.typography.subtitle.b22,
                color = HapHapTheme.colors.gray800,
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "소중한 결과를 등록해 주셔서 감사해요",
                style = HapHapTheme.typography.body.m14,
                color = HapHapTheme.colors.gray500,
            )

            Spacer(modifier = Modifier.height(126.dp))

            Image(
                painter = painterResource(R.drawable.img_register_check),
                contentDescription = null,
                modifier = Modifier
                    .size(210.dp)
                    .align(Alignment.CenterHorizontally),
            )

            Spacer(modifier = Modifier.weight(1f))

            HapHapBasicButton(
                text = "완료",
                textStyle = HapHapTheme.typography.body.b18,
                colorType = ButtonType.Primary(enabled = true),
                onClick = onCompleteClick,
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterCompleteSectionPreview() {
    HapHapTheme {
        RegisterCompleteSection(
            onBackClick = {},
            onCompleteClick = {},
        )
    }
}