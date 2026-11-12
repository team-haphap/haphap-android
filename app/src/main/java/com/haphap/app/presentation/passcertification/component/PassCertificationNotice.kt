package com.haphap.app.presentation.passcertification.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun PassCertificationNotice(
    modifier: Modifier = Modifier,
    text: String = "인증 반려는 전형 불합격을 의미하지 않아요.",
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(
            space = 4.dp,
            alignment = Alignment.CenterHorizontally,
        ),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_info_20),
            contentDescription = null,
            tint = Color.Unspecified,
        )

        Text(
            text = text,
            style = HapHapTheme.typography.body.sb14,
            color = HapHapTheme.colors.gray300,
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PassCertificationNoticePreview() {
    HapHapTheme {
        PassCertificationNotice()
    }
}
