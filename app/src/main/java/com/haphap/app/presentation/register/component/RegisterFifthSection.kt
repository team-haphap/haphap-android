package com.haphap.app.presentation.register.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun RegisterFifthSection(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.padding(start = 20.dp, end = 20.dp, top = 36.dp, bottom = 10.dp),
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

        Spacer(modifier = Modifier.weight(136f / 346f))

        Image(
            painter = painterResource(R.drawable.img_register_check),
            contentDescription = null,
            modifier = Modifier
                .size(210.dp)
                .align(Alignment.CenterHorizontally),
        )

        Spacer(modifier = Modifier.weight(210f / 346f))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterFifthSectionPreview() {
    HapHapTheme {
        RegisterFifthSection()
    }
}