package com.haphap.app.core.designsystem.component.toast

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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme

@Composable
fun HapHapToast(
    text: String,
    isAlarm: Boolean,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = HapHapTheme.colors.gray600,
                shape = RoundedCornerShape(6.dp),
            )
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if(isAlarm){
            Icon(
                imageVector = ImageVector.vectorResource(R.drawable.ic_top_bar_alarm_default_44),
                contentDescription = null,
                modifier = Modifier.size(32.dp),
                tint = HapHapTheme.colors.white,
            )
        }

        Spacer(modifier = Modifier.width(6.dp))

        Text(
            text = text,
            style = HapHapTheme.typography.body.sb13,
            color = HapHapTheme.colors.white,
            modifier = Modifier.padding(vertical = 11.dp)
        )

    }
}

@Preview
@Composable
private fun HapHapToastPreview() {
    HapHapTheme{
        Column{
            HapHapToast(
                text = "이미 등록한 공고입니다",
                isAlarm = true,
            )

            Spacer(modifier = Modifier.height(10.dp))

            HapHapToast(
                text = "이미 등록한 공고입니다",
                isAlarm = false,
            )
        }
    }
}
