package com.haphap.app.presentation.joblist.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun IconEmptyComponent(
    text: String,
    modifier: Modifier = Modifier,
) {
    Column (
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_alert_53),
            contentDescription = null,
            tint = HapHapTheme.colors.gray200,
        )

        Text(
            text = text,
            color = HapHapTheme.colors.gray400,
            style = HapHapTheme.typography.caption.sb12,
        )
    }

}

@Preview
@Composable
private fun IconEmptyComponentPreview() {
    HapHapTheme{
        IconEmptyComponent(
            text = "해당 카테고리에 등록된 공고가 없습니다.",
        )
    }
}
