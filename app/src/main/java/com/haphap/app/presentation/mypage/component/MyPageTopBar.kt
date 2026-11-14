package com.haphap.app.presentation.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

@Composable
fun MyPageTopBar(
    onBackClick: () -> Unit,
    onSettingClick: () -> Unit,
    onAlarmClick: () -> Unit,
    modifier: Modifier = Modifier,
    alarmCount: Int = 0,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back_30),
            contentDescription = null,
            tint = HapHapTheme.colors.gray800,
            modifier = Modifier.noRippleClickable(onClick = onBackClick),
        )

        Spacer(modifier = Modifier.width(10.dp))

        Text(
            text = "마이페이지",
            style = HapHapTheme.typography.subtitle.b20,
            color = HapHapTheme.colors.gray800,
            modifier = Modifier.weight(1f),
        )

        MyPageAlarmIcon(
            alarmCount = alarmCount,
            onAlarmClick = onAlarmClick,
        )

        Spacer(modifier = Modifier.width(16.dp))

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_setting_32),
            contentDescription = null,
            tint = HapHapTheme.colors.gray700,
            modifier = Modifier.noRippleClickable(onClick = onSettingClick),
        )
    }
}


//Todo: 아이콘 교체 필요. 임시 구현
@Composable
private fun MyPageAlarmIcon(
    alarmCount: Int,
    onAlarmClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(36.dp)
            .noRippleClickable(onClick = onAlarmClick),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_bell_filled_24),
            contentDescription = null,
            tint = HapHapTheme.colors.gray700,
            modifier = Modifier
                .size(32.dp)
                .align(Alignment.Center),
        )

        if (alarmCount > 0) {
            Text(
                text = if (alarmCount > 99) "99+" else alarmCount.toString(),
                style = HapHapTheme.typography.caption.sb10,
                color = HapHapTheme.colors.white,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .defaultMinSize(minWidth = 16.dp, minHeight = 16.dp)
                    .background(
                        color = HapHapTheme.colors.primary500,
                        shape = CircleShape,
                    )
                    .padding(horizontal = 4.dp, vertical = 1.dp),
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun MyPageTopBarPreview() {
    HapHapTheme {
        MyPageTopBar(
            onBackClick = {},
            onSettingClick = {},
            alarmCount = 3,
            onAlarmClick = {},
        )
    }
}
