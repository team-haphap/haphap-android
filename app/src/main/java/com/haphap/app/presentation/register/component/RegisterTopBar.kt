package com.haphap.app.presentation.register.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

@Composable
fun RegisterTopBar(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    isText: Boolean = true,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back_30),
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.noRippleClickable(onClick = onBackClick), // 수정: 뒤로가기 클릭 처리 추가
        )

        Spacer(modifier = Modifier.width(10.dp))

        if (isText) Text(
            text = "내 상태 등록",
            style = HapHapTheme.typography.subtitle.b20,
            color = HapHapTheme.colors.gray800,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun RegisterTopBarPreview() {
    HapHapTheme {
        RegisterTopBar(
            onBackClick = {},
        )
    }
}