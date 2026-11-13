package com.haphap.app.presentation.passcertification.detail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun PassCertificationRejectReason(
    reason: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(6.dp))
            .background(color = HapHapTheme.colors.subred2)
            .padding(horizontal = 10.dp, vertical = 12.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_alert_red_20),
                contentDescription = null,
                tint = Color.Unspecified,
            )

            Text(
                text = "반려 사유",
                style = HapHapTheme.typography.body.b14,
                color = HapHapTheme.colors.subred,
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = reason,
                style = HapHapTheme.typography.caption.sb12,
                color = HapHapTheme.colors.gray500,
            )

            Text(
                text = "내용이 잘 보이는 이미지로 다시 제출해주세요",
                style = HapHapTheme.typography.caption.r10,
                color = HapHapTheme.colors.gray500,
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun PassCertificationRejectReasonPreview() {
    HapHapTheme {
        PassCertificationRejectReason(
            reason = "첨부 이미지에서 공고명과 합격 여부를 확인하기 어려워요.",
            modifier = Modifier.padding(horizontal = 20.dp),
        )
    }
}
