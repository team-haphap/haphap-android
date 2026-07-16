package com.haphap.app.core.designsystem.component.button

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.haphap.app.R
import com.haphap.app.core.designsystem.theme.HapHapTheme
import com.haphap.app.core.extensions.noRippleClickable

private const val ROTATION_DURATION_MILLIS = 500

@Composable
fun HapHapRefreshButton(
    onButtonClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var rotationTarget by remember { mutableFloatStateOf(0f) }

    val rotation by animateFloatAsState(
        targetValue = rotationTarget,
        animationSpec = tween(durationMillis = ROTATION_DURATION_MILLIS),
        label = "refreshButton",
    )

    Box(
        modifier = modifier
            .background(
                color = HapHapTheme.colors.gray500,
                shape = CircleShape,
            )
            .noRippleClickable(
                onClick = {
                    rotationTarget += 360f
                    onButtonClick()
                },
            )
            .padding(13.dp),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_refresh_18),
            contentDescription = null,
            tint = HapHapTheme.colors.white,
            modifier = Modifier
                .size(18.dp)
                .rotate(rotation),
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HapHapRefreshButtonPreview() {
    HapHapTheme {
        HapHapRefreshButton(onButtonClick = {})
    }
}
