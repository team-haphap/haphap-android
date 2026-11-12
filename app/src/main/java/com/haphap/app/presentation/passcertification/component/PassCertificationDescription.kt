package com.haphap.app.presentation.passcertification.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun PassCertificationDescription(
    modifier: Modifier = Modifier,
    text: String = "반려된 합격 인증 내역이에요.\n내용을 확인한 후 다시 제출해 주세요.",
) {
    Text(
        text = text,
        style = HapHapTheme.typography.body.sb13,
        color = HapHapTheme.colors.gray400,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PassCertificationDescriptionPreview() {
    HapHapTheme {
        PassCertificationDescription()
    }
}
