package com.haphap.app.presentation.register.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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

@Composable
fun RegisterAddImageGuide(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(8.dp))
            .background(color = HapHapTheme.colors.gray100)
            .padding(all = 10.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_alert_53),
                contentDescription = null,
                tint = HapHapTheme.colors.gray400,
                modifier = Modifier.size(22.dp),
            )

            Spacer(modifier = Modifier.width(4.dp))

            Text(
                text = "첨부 가이드",
                style = HapHapTheme.typography.caption.b12,
                color = HapHapTheme.colors.gray800,
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        RegisterGuideItem(
            guideText = "기업명, 공고명(직무), 합격자명, 전형단계가 반드시 보여야해요.",
        )
        RegisterGuideItem(
            guideText = "다른 개인정보는 가려도 괜찮아요.",
        )
        RegisterGuideItem(
            guideText = "한 장에 모든 정보가 안 담기면 여러 장을 첨부해주세요.",
        )
        RegisterGuideItem(
            guideText = "각 이미지당 최대 10MB까지 업로드 가능해요.",
        )
        RegisterGuideItem(
            guideText = "JPG, JPEG, PNG, HEIC 형식을 지원해요.",
        )
    }
}

@Composable
private fun RegisterGuideItem(
    guideText: String,
) {
    Text(
        text = "• $guideText",
        style = HapHapTheme.typography.caption.r11,
        color = HapHapTheme.colors.gray800,
    )
}

@Preview(showBackground = true)
@Composable
private fun RegisterAddImageGuidePreview() {
    HapHapTheme {
        RegisterAddImageGuide()
    }
}
