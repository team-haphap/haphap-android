package com.haphap.app.presentation.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
fun HomeErrorComponent(
    text: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 90.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_alert_53),
            contentDescription = null,
            tint = HapHapTheme.colors.gray200,
            modifier = Modifier.size(53.dp),
        )

        Spacer(modifier = Modifier.height(12.dp))

        Text(
            text = text,
            style = HapHapTheme.typography.caption.sb12,
            color = HapHapTheme.colors.gray400,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeErrorComponentPreview() {
    HapHapTheme {
        HomeErrorComponent(
            text = "네트워크 연결을 확인해주세요",
        )
    }
}