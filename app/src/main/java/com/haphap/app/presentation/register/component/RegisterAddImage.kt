package com.haphap.app.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.dashedBorder
import com.haphap.app.core.extensions.noRippleClickable
import com.haphap.app.presentation.register.type.RegisterAddImageType

@Composable
fun RegisterAddImage(
    type: RegisterAddImageType,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val contentPadding = when (type) {
        RegisterAddImageType.INITIAL -> PaddingValues(start = 94.dp, top = 48.dp, end = 94.dp, bottom = 43.dp)
        RegisterAddImageType.ADDITIONAL -> PaddingValues(horizontal = 73.dp, vertical = 18.dp)
    }

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(HapHapTheme.colors.gray100)
            .dashedBorder(
                color = HapHapTheme.colors.gray200,
                cornerRadius = 12.dp,
            )
            .noRippleClickable(onClick = onClick)
            .padding(contentPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        when (type) {
            RegisterAddImageType.INITIAL -> {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_register_add_image_42),
                    contentDescription = null,
                    tint = HapHapTheme.colors.gray600,
                    modifier = Modifier.size(42.dp),
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "합격 인증 첨부",
                    style = HapHapTheme.typography.body.sb14,
                    color = HapHapTheme.colors.gray800,
                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = "이미지 파일을 첨부해주세요",
                    style = HapHapTheme.typography.caption.r11,
                    color = HapHapTheme.colors.gray500,
                )

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "(최대 3개 첨부)",
                    style = HapHapTheme.typography.caption.r11,
                    color = HapHapTheme.colors.gray400,
                )
            }

            RegisterAddImageType.ADDITIONAL -> {
                Text(
                    text = "이미지 추가",
                    style = HapHapTheme.typography.body.sb14,
                    color = HapHapTheme.colors.gray800,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "최대 3개까지 첨부 할 수 있습니다",
                    style = HapHapTheme.typography.caption.sb12,
                    color = HapHapTheme.colors.gray500,
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterAddImageInitialPreview() {
    HapHapTheme {
        RegisterAddImage(
            type = RegisterAddImageType.INITIAL,
            onClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RegisterAddImageAdditionalPreview() {
    HapHapTheme {
        RegisterAddImage(
            type = RegisterAddImageType.ADDITIONAL,
            onClick = {},
        )
    }
}
