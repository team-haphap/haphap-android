package com.haphap.app.presentation.setting.accountinfo.component

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
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
fun AccountInfoItem(
    label: String,
    content: String,
    modifier: Modifier = Modifier,
    @DrawableRes trailingImgRes: Int? = null,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(top  = 16.dp),
    ) {
        Text(
            text = label,
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray700,
            modifier = Modifier.padding(vertical = 10.dp),
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = content,
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 4.dp),
                style = HapHapTheme.typography.body.m14,
                color = HapHapTheme.colors.gray500,
            )

            trailingImgRes?.let {
                Image(
                    painter = painterResource(id = it),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(
            color = HapHapTheme.colors.gray200,
            thickness = 1.dp,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun AccountInfoItemPreview() {
    HapHapTheme {
        AccountInfoItem(
            label = "이메일",
            content = "email@example.com",
            trailingImgRes = R.drawable.img_kakao,
        )
    }
}
