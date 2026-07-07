package com.haphap.app.core.designsystem.component.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
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
fun HapHapRefreshButton(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .size(44.dp)
            .background(
                color = HapHapTheme.colors.gray500,
                shape = CircleShape,
            )
            .padding(13.dp)
            .noRippleClickable(onClick = onButtonClick),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_refresh_18),
            contentDescription = null,
            tint = HapHapTheme.colors.white,
            modifier = Modifier.size(18.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeRefreshButtonPreview() {
    HapHapTheme {
        HapHapRefreshButton(onButtonClick = {})
    }
}