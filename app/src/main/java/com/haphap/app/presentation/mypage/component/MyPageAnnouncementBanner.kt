package com.haphap.app.presentation.mypage.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

@Composable
fun MyPageAnnouncementBanner(
    onBannerClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .background(
                color = HapHapTheme.colors.gray600,
                shape = RoundedCornerShape(6.dp)
            )
            .noRippleClickable(onClick = onBannerClick)
            .padding(horizontal = 12.dp, vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_bell_filled_24),
            contentDescription = null,
            tint = HapHapTheme.colors.gray200,
            modifier = Modifier.size(24.dp),
        )

        Spacer(modifier = Modifier.width(2.dp))

        Text(
            text = "이번 주 발표가 예상되는 공고가 있어요.",
            style = HapHapTheme.typography.caption.sb12,
            color = HapHapTheme.colors.white,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.weight(1f),
        )

        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_right_24),
            contentDescription = null,
            tint = HapHapTheme.colors.gray300,
            modifier = Modifier.size(24.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MyPageAnnouncementBannerPreview() {
    HapHapTheme {
        MyPageAnnouncementBanner(
            onBannerClick = {},
        )
    }
}
