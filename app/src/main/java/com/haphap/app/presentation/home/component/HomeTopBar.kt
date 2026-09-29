package com.haphap.app.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

@Composable
fun HomeTopBar(
    point: Int,
    onPointClick: () -> Unit,
    onProfileClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = HapHapTheme.colors.white)
            .padding(horizontal = 20.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_text_logo),
            contentDescription = null,
            modifier = Modifier.height(20.dp),
        )

        Spacer(modifier = Modifier.weight(1f))

        HomePointChip(
            point = point,
            onPointClick = onPointClick,
        )

        Spacer(modifier = Modifier.width(6.dp))

        Icon(
            imageVector = ImageVector.vectorResource(R.drawable.ic_bottom_bar_my_24),
            contentDescription = "프로필",
            tint = HapHapTheme.colors.gray500,
            modifier = Modifier
                .size(24.dp)
                .noRippleClickable(onClick = onProfileClick),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeTopBarPreview() {
    HapHapTheme {
        HomeTopBar(
            point = 100,
            onPointClick = {},
            onProfileClick = {},
        )
    }
}