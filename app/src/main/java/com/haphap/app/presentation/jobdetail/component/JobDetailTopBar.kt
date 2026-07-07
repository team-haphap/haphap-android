package com.haphap.app.presentation.jobdetail.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

@Composable
fun JobDetailTopBar(
    onBackClick: () -> Unit,
    onAlarmClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier,
    isAlarmActive: Boolean = false,
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .background(HapHapTheme.colors.white)
            .height(44.dp),
        color = HapHapTheme.colors.white
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val alarmIconRes = if (isAlarmActive) {
                R.drawable.ic_top_bar_alarm_selected_44
            } else {
                R.drawable.ic_top_bar_alarm_default_44
            }

            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_back_30),
                contentDescription = "뒤로가기",
                modifier = Modifier.noRippleClickable (onClick = { onBackClick() })
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = alarmIconRes),
                    contentDescription = "알림",
                    modifier = Modifier.noRippleClickable(onClick = { onAlarmClick() })
                )

                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_top_bar_more_44),
                    contentDescription = "더보기",
                    modifier = Modifier.noRippleClickable (onClick ={ onMoreClick() })
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun JobDetailTopBarPreview() {
    HapHapTheme {
        JobDetailTopBar(
            onBackClick = {},
            onAlarmClick = {},
            onMoreClick = {}
        )
    }
}